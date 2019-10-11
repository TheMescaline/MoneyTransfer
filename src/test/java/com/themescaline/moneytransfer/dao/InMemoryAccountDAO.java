package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.Account;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
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
        final Account account = storage.get(accountId);
        if (account == null) {
            throw new NotFoundException("not founded");
        }
        return account;
    }

    @Override
    public Account save(Account account) {
        if (account.getBalance() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "balance must not be negative");
        }
        account.setId(counter.incrementAndGet());
        storage.put(account.getId(), account);
        return account;
    }

    @Override
    public Account update(Account account) {
        if (account.getBalance() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "balance must not be negative");
        }
        if (null == storage.replace(account.getId(), account)) {
            throw new NotFoundException("not founded for update");
        }
        return account;
    }

    @Override
    public void delete(long accountId) {
        if (storage.remove(accountId) == null) {
            throw new NotFoundException("not founded for delete");
        }
    }

    @Override
    public void doTransfer(long fromAccountId, long toAccountId, double amount) {
        Account from = storage.get(fromAccountId);
        Account to = storage.get(toAccountId);
        if (from == null) {
            throw new NotFoundException(MessageFormat.format("Can't find account with id {0}", fromAccountId));
        }
        if (to == null) {
            throw new NotFoundException(MessageFormat.format("Can't find account with id {0}", toAccountId));
        }
        if (from.getBalance() < amount) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), MessageFormat.format("Not enough money on account with id {1} to make transfer", fromAccountId));
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        storage.put(from.getId(), from);
        storage.put(to.getId(), to);
    }

    @Override
    public void clear() {
        storage.clear();
        counter.set(0L);
    }
}
