package com.themescaline.moneytransfer.util;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * Implementation of a TransactionHelper with no locks.
 * Used by Executor Service as Callable
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
public class NotLockedTransactionHelper<T> extends TransactionHelper<T> implements Callable<T> {

    @Builder
    public NotLockedTransactionHelper(TransactionalExecutor<T> transactionalExecutor) {
        super(transactionalExecutor);
    }

    @Override
    public T call() throws InterruptedException {
        return execute();
    }
}
