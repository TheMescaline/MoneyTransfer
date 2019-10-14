package com.themescaline.moneytransfer.util.multithread;

import com.themescaline.moneytransfer.exceptions.ExecutionExceptionWrapper;
import com.themescaline.moneytransfer.exceptions.UncategorizedException;
import com.themescaline.moneytransfer.util.ExceptionMessage;
import com.themescaline.moneytransfer.util.TransactionHelper;
import com.themescaline.moneytransfer.util.TransactionalExecutor;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static com.themescaline.moneytransfer.util.ExceptionMessage.CONCURRENT;

@Slf4j
public class TryCatchWrapper<T> {
    public static <T> T wrap(ExecutorService service, TransactionalExecutor<T> executor) {
        try {
            return service.submit(new TransactionHelper<>(executor)).get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new UncategorizedException(ExceptionMessage.getFormatted(CONCURRENT, e.getMessage()));
        } catch (ExecutionException e) {
            throw new ExecutionExceptionWrapper(e);
        }
    }
}
