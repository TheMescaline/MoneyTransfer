package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.model.Account;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
        return storage.get(accountId);
    }

    @Override
    public Account save(Account account) {
        if (account.isNew()) {
            account.setId(counter.incrementAndGet());
        }

        if (null != storage.putIfAbsent(account.getId(), account)) {
            return null;
        }
        return account;
    }

    @Override
    public Account update(long accountId, Account account) {
        account.setId(accountId);
        if (null == storage.replace(accountId, account)) {
            return null;
        }
        return account;
    }

    @Override
    public boolean delete(long accountId) {
        return null != storage.remove(accountId);
    }

    @Override
    public boolean doTransfer(long fromAccountId, long toAccountId, double amount) {
        Account from = storage.get(fromAccountId);
        Account to = storage.get(toAccountId);
        if (from != null && to != null && amount >=0 && from.getBalance() >= amount) {
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            storage.put(from.getId(), from);
            storage.put(to.getId(), to);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        storage.clear();
        counter.set(0L);
    }
}
