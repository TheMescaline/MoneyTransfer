package com.themescaline.moneytransfer.util.multithread;

import com.themescaline.moneytransfer.exceptions.ExceptionMessage;
import com.themescaline.moneytransfer.exceptions.ExecutionExceptionWrapper;
import com.themescaline.moneytransfer.exceptions.UncategorizedException;
import com.themescaline.moneytransfer.util.DoubleLockedTransactionHelper;
import com.themescaline.moneytransfer.util.LockedTransactionHelper;
import com.themescaline.moneytransfer.util.NotLockedTransactionHelper;
import com.themescaline.moneytransfer.util.TransactionalExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.CONCURRENT;

/**
 * Strategy pattern class reduces boilerplate try/catches for ExecutorService get() method
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
public class TryCatchWrapper<T> {
    /**
     * Don't using any locks inside
     *
     * @param service  executor service
     * @param executor strategy for execution
     * @param <T>      return type of a strategy
     * @return result of a strategy
     */
    public static <T> T wrapWithoutLock(ExecutorService service, TransactionalExecutor<T> executor) {
        return wrap(service, new NotLockedTransactionHelper<>(executor));
    }

    /**
     * Using lock on a single object
     *
     * @param service               executor service
     * @param accountId             account id
     * @param currentLockedAccounts ConcurrentMap, that contains locker objects
     * @param executor              strategy for execution
     * @param <T>                   return type of a strategy
     * @return result of a strategy
     */
    public static <T> T wrapWithSingleLock(ExecutorService service, long accountId, ConcurrentMap<Long, AccountLocker> currentLockedAccounts, TransactionalExecutor<T> executor) {
        return wrap(service, new LockedTransactionHelper<>(accountId, currentLockedAccounts, executor));
    }

    /**
     * Using lock on two objects
     *
     * @param service               executor service
     * @param fromAccountId         account id
     * @param toAccountId           account id
     * @param currentLockedAccounts ConcurrentMap, that contains locker objects
     * @param executor              strategy for execution
     * @param <T>                   return type of a strategy
     * @return result of a strategy
     */
    public static <T> T wrapWithDoubleLock(ExecutorService service, long fromAccountId, long toAccountId, ConcurrentMap<Long, AccountLocker> currentLockedAccounts, TransactionalExecutor<T> executor) {
        return wrap(service, new DoubleLockedTransactionHelper<>(fromAccountId, toAccountId, currentLockedAccounts, executor));
    }

    private static <T> T wrap(ExecutorService service, Callable<T> callable) {
        try {
            return service.submit(callable).get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new UncategorizedException(ExceptionMessage.getFormatted(CONCURRENT, e.getMessage()));
        } catch (ExecutionException e) {
            throw new ExecutionExceptionWrapper(e);
        }
    }
}
