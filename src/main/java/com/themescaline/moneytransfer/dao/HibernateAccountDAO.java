package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.config.HibernateSessionFactoryHelper;
import com.themescaline.moneytransfer.exceptions.AccountNotFoundException;
import com.themescaline.moneytransfer.exceptions.BalanceException;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.multithread.AccountLocker;
import com.themescaline.moneytransfer.util.multithread.TryCatchWrapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.NOT_ENOUGH_MONEY;

/**
 * Account DAO implementation with concurrent access based on concurrency
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
        return TryCatchWrapper.wrapWithoutLock(service, session -> session.createQuery(ALL_SORTED).list());
    }

    @Override
    public Account getOne(long accountId) {
        return TryCatchWrapper.wrapWithoutLock(service, session -> {
            Account result = session.find(Account.class, accountId);
            checkNotFound(result, accountId);
            return result;
        });
    }

    @Override
    public Account save(Account account) {
        return TryCatchWrapper.wrapWithoutLock(service, session -> {
            session.save(account);
            return account;
        });
    }

    @Override
    public Account update(Account account) {
        return TryCatchWrapper.wrapWithSingleLock(service, account.getId(), currentLockedAccounts, session -> {
            Account updated = session.find(Account.class, account.getId());
            checkNotFound(updated, account.getId());
            updated.setBalance(account.getBalance());
            session.update(updated);
            return updated;
        });
    }

    @Override
    public void delete(long accountId) {
        TryCatchWrapper.wrapWithSingleLock(service, accountId, currentLockedAccounts, session -> {
            Account toDelete = session.find(Account.class, accountId);
            checkNotFound(toDelete, accountId);
            session.delete(toDelete);
            return null;

        });
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, BigDecimal amount) {
        TryCatchWrapper.wrapWithDoubleLock(service, fromAccountId, toAccountId, currentLockedAccounts, session -> {
            Account fromAccount = session.find(Account.class, fromAccountId);
            Account toAccount = session.find(Account.class, toAccountId);
            checkNotFound(fromAccount, fromAccountId);
            checkNotFound(toAccount, toAccountId);
            checkNotEnoughMoney(amount, fromAccount);
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            session.merge(fromAccount);
            toAccount.setBalance(toAccount.getBalance().add(amount));
            session.merge(toAccount);
            return null;
        });
    }

    @Override
    public void withdraw(long accountId, BigDecimal amount) {
        TryCatchWrapper.wrapWithSingleLock(service, accountId, currentLockedAccounts, session -> {
            Account account = session.find(Account.class, accountId);
            checkNotFound(account, accountId);
            checkNotEnoughMoney(amount, account);
            account.setBalance(account.getBalance().subtract(amount));
            session.merge(account);
            return null;
        });
    }

    @Override
    public void deposit(long accountId, BigDecimal amount) {
        TryCatchWrapper.wrapWithSingleLock(service, accountId, currentLockedAccounts, session -> {
            Account account = session.find(Account.class, accountId);
            checkNotFound(account, accountId);
            account.setBalance(account.getBalance().add(amount));
            session.merge(account);
            return null;
        });
    }

    private void checkNotFound(Account account, long expectedAccountId) {
        if (account == null) {
            throw new AccountNotFoundException(expectedAccountId);
        }
    }

    private void checkNotEnoughMoney(BigDecimal amount, Account account) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BalanceException(NOT_ENOUGH_MONEY, String.valueOf(account.getId()));
        }
    }
}
