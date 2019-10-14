package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.exceptions.AccountNotFoundException;
import com.themescaline.moneytransfer.exceptions.BalanceException;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.themescaline.moneytransfer.util.ExceptionMessage.NEGATIVE_BALANCE;

@Slf4j
@Singleton
public class InMemoryAccountDAO implements AccountDAO {
    private final AtomicLong counter = new AtomicLong(0L);
    private final ConcurrentMap<Long, Account> storage = new ConcurrentHashMap<>();

    @Override
    public List<Account> getAll() {
        return storage.values().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
    }

    @Override
    public Account getOne(long accountId) {
        final Account account = storage.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId);
        }
        return account;
    }

    @Override
    public Account save(Account account) {
        if (account.getBalance() < 0) {
            throw new BalanceException(NEGATIVE_BALANCE);
        }
        account.setId(counter.incrementAndGet());
        storage.put(account.getId(), account);
        return account;
    }

    @Override
    public Account update(Account account) {
        if (account.getBalance() < 0) {
            throw new BalanceException(NEGATIVE_BALANCE);
        }
        if (null == storage.replace(account.getId(), account)) {
            throw new AccountNotFoundException(account.getId());
        }
        return account;
    }

    @Override
    public void delete(long accountId) {
        if (storage.remove(accountId) == null) {
            throw new AccountNotFoundException(accountId);
        }
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, double amount) {
        Account from = storage.get(fromAccountId);
        Account to = storage.get(toAccountId);
        if (from == null) {
            throw new AccountNotFoundException(fromAccountId);
        }
        if (to == null) {
            throw new AccountNotFoundException(toAccountId);
        }
        if (from.getBalance() < amount) {
            throw new BalanceException(ExceptionMessage.NOT_ENOUGH_MONEY, String.valueOf(fromAccountId));
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        storage.put(from.getId(), from);
        storage.put(to.getId(), to);
    }

    @Override
    public void withdraw(long accountId, double amount) {
        Account from = storage.get(accountId);
        if (from == null) {
            throw new AccountNotFoundException(accountId);
        }
        if (from.getBalance() < amount) {
            throw new BalanceException(ExceptionMessage.NOT_ENOUGH_MONEY, String.valueOf(accountId));
        }
        from.setBalance(from.getBalance() - amount);
        storage.put(from.getId(), from);
    }

    @Override
    public void deposit(long accountId, double amount) {
        Account to = storage.get(accountId);
        if (to == null) {
            throw new AccountNotFoundException(accountId);
        }
        to.setBalance(to.getBalance() + amount);
        storage.put(to.getId(), to);
    }

    @Override
    public void clear() {
        storage.clear();
        counter.set(0L);
    }
}
