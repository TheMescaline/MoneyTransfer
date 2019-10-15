package com.themescaline.moneytransfer.util;

import com.themescaline.moneytransfer.exceptions.ConcurrentLockException;
import com.themescaline.moneytransfer.util.multithread.AccountLocker;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.themescaline.moneytransfer.dao.HibernateAccountDAO.LOCK_WAITING_SECONDS;

/**
 * Implementation of a TransactionHelper with two locks.
 * Used by Executor Service as Callable
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
public class DoubleLockedTransactionHelper<T> extends TransactionHelper<T> implements Callable<T> {
    private final long fromAccountId;
    private final long toAccountId;
    private final ConcurrentMap<Long, AccountLocker> currentLockedAccounts;

    public DoubleLockedTransactionHelper(long fromAccountId, long toAccountId, ConcurrentMap<Long, AccountLocker> currentLockedAccounts, TransactionalExecutor<T> transactionalExecutor) {
        super(transactionalExecutor);
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.currentLockedAccounts = currentLockedAccounts;
    }

    @Override
    public T call() throws InterruptedException {
        long firstLockedId = Math.min(fromAccountId, toAccountId);
        long secondLockedId = Math.max(fromAccountId, toAccountId);
        currentLockedAccounts.putIfAbsent(firstLockedId, new AccountLocker(firstLockedId));
        if (currentLockedAccounts.get(firstLockedId).getLock().tryLock(LOCK_WAITING_SECONDS, TimeUnit.SECONDS)) {
            try {
                currentLockedAccounts.putIfAbsent(secondLockedId, new AccountLocker(secondLockedId));
                if (currentLockedAccounts.get(secondLockedId).getLock().tryLock(LOCK_WAITING_SECONDS, TimeUnit.SECONDS)) {
                    try {
                        return execute();
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
    }
}
