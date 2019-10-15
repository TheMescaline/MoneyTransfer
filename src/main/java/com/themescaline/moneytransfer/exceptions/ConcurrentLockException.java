package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.core.Response;

import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.ACCOUNT_LOCKED;

/**
 * Unlock wait timeout exception
 *
 * @author lex.korovin@gmail.com
 */
public class ConcurrentLockException extends AppException {
    /**
     * @param accountId account Id
     */
    public ConcurrentLockException(long accountId) {
        super(Response.Status.INTERNAL_SERVER_ERROR, ExceptionMessage.getFormatted(ACCOUNT_LOCKED, String.valueOf(accountId)));
    }
}
