package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.config.HibernateSessionFactoryHelper;
import com.themescaline.moneytransfer.exceptions.AccountNotFoundException;
import com.themescaline.moneytransfer.exceptions.BalanceException;
import com.themescaline.moneytransfer.exceptions.ConcurrentLockException;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.TransactionHelper;
import com.themescaline.moneytransfer.util.multithread.AccountLocker;
import com.themescaline.moneytransfer.util.multithread.TryCatchWithLockWrapper;
import com.themescaline.moneytransfer.util.multithread.TryCatchWrapper;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.themescaline.moneytransfer.util.ExceptionMessage.NOT_ENOUGH_MONEY;

/**
 * Account DAO implementation
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@Singleton
public class HibernateAccountDAO implements AccountDAO {
    public static final long LOCK_WAITING_SECONDS = 4;
    private final ExecutorService service = Executors.newFixedThreadPool(HibernateSessionFactoryHelper.getPoolSize());
    private final ConcurrentMap<Long, AccountLocker> currentLockedAccounts = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public List<Account> getAll() {
        return TryCatchWrapper.wrap(service, session -> session.createQuery(ALL_SORTED).list());
    }

    @Override
    public Account getOne(long accountId) {
        return TryCatchWrapper.wrap(service, session -> {
            Account result = session.find(Account.class, accountId);
            checkNotFound(result, accountId);
            return result;
        });
    }

    @Override
    public Account save(Account account) {
        return TryCatchWrapper.wrap(service, session -> {
            session.save(account);
            return account;
        });
    }

    @Override
    public Account update(Account account) {
        return TryCatchWithLockWrapper.wrap(service, account.getId(), currentLockedAccounts, session -> {
            Account updated = session.find(Account.class, account.getId());
            checkNotFound(updated, account.getId());
            updated.setBalance(account.getBalance());
            session.update(updated);
            return updated;
        });
    }

    @Override
    public void delete(long accountId) {
        TryCatchWithLockWrapper.wrap(service, accountId, currentLockedAccounts, session -> {

            Account toDelete = session.find(Account.class, accountId);
            checkNotFound(toDelete, accountId);
            session.delete(toDelete);
            return null;

        });
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, double amount) {
        TryCatchWrapper.wrap(service, session -> {
            long firstLockedId = Math.min(fromAccountId, toAccountId);
            long secondLockedId = Math.max(fromAccountId, toAccountId);
            currentLockedAccounts.putIfAbsent(firstLockedId, new AccountLocker(firstLockedId));
            if (currentLockedAccounts.get(firstLockedId).getLock().tryLock(LOCK_WAITING_SECONDS, TimeUnit.SECONDS)) {
                try {
                    currentLockedAccounts.putIfAbsent(secondLockedId, new AccountLocker(secondLockedId));
                    if (currentLockedAccounts.get(secondLockedId).getLock().tryLock(LOCK_WAITING_SECONDS, TimeUnit.SECONDS)) {
                        try {
                            Account fromAccount = session.find(Account.class, fromAccountId);
                            Account toAccount = session.find(Account.class, toAccountId);
                            checkNotFound(fromAccount, fromAccountId);
                            checkNotFound(toAccount, toAccountId);
                            checkNotEnoughMoney(amount, fromAccount);
                            fromAccount.setBalance(fromAccount.getBalance() - amount);
                            session.merge(fromAccount);
                            toAccount.setBalance(toAccount.getBalance() + amount);
                            session.merge(toAccount);
                        } finally {
                            currentLockedAccounts.get(secondLockedId).getLock().unlock();
                        }
                    } else {
                        throw new ConcurrentLockException(secondLockedId);
                    }
                } finally {
                    currentLockedAccounts.get(firstLockedId).getLock().unlock();
                }
            } else {
                throw new ConcurrentLockException(firstLockedId);
            }
            return null;
        });
    }

    @Override
    public void withdraw(long accountId, double amount) {
        TryCatchWithLockWrapper.wrap(service, accountId, currentLockedAccounts, session -> {
            Account account = session.find(Account.class, accountId);
            checkNotFound(account, accountId);
            checkNotEnoughMoney(amount, account);
            account.setBalance(account.getBalance() - amount);
            session.merge(account);
            return null;
        });
    }

    @Override
    public void deposit(long accountId, double amount) {
        TryCatchWithLockWrapper.wrap(service, accountId, currentLockedAccounts, session -> {
            Account account = session.find(Account.class, accountId);
            checkNotFound(account, accountId);
            account.setBalance(account.getBalance() + amount);
            session.merge(account);
            return null;
        });
    }

    @Override
    public void clear() {
        service.submit(new TransactionHelper<>(session -> session.createQuery(CLEAR).executeUpdate()));
    }

    private void checkNotFound(Account account, long expectedAccountId) {
        if (account == null) {
            throw new AccountNotFoundException(expectedAccountId);
        }
    }

    private void checkNotEnoughMoney(double amount, Account account) {
        if (account.getBalance() < amount) {
            throw new BalanceException(NOT_ENOUGH_MONEY, String.valueOf(account.getId()));
        }
    }
}
