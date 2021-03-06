package com.themescaline.moneytransfer.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.ExecutionException;

/**
 * Wrapper for ExecutionExceptions that are thrown from executor service
 *
 * @author lex.korovin@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExecutionExceptionWrapper extends RuntimeException {
    private ExecutionException nested;

    public ExecutionExceptionWrapper(ExecutionException e) {
        super(e.getMessage());
        this.nested = e;
    }
}
