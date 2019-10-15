package com.themescaline.moneytransfer.util;

import com.themescaline.moneytransfer.exceptions.ConcurrentLockException;
import com.themescaline.moneytransfer.util.multithread.AccountLocker;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.themescaline.moneytransfer.dao.HibernateAccountDAO.LOCK_WAITING_SECONDS;

/**
 * Implementation of a TransactionHelper with one lock.
 * Used by Executor Service as Callable
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
public class LockedTransactionHelper<T> extends TransactionHelper<T> implements Callable<T> {
    private final long accountId;
    private final ConcurrentMap<Long, AccountLocker> currentLockedAccounts;

    @Builder
    public LockedTransactionHelper(long accountId, ConcurrentMap<Long, AccountLocker> currentLockedAccounts, TransactionalExecutor<T> transactionalExecutor) {
        super(transactionalExecutor);
        this.accountId = accountId;
        this.currentLockedAccounts = currentLockedAccounts;
    }

    @Override
    public T call() throws InterruptedException {
        currentLockedAccounts.putIfAbsent(accountId, new AccountLocker(accountId));
        if (currentLockedAccounts.get(accountId).getLock().tryLock(LOCK_WAITING_SECONDS, TimeUnit.SECONDS)) {
            try {
                return execute();
            } finally {
                currentLockedAccounts.get(accountId).getLock().unlock();
            }
        } else {
            throw new ConcurrentLockException(accountId);
        }
    }
}
