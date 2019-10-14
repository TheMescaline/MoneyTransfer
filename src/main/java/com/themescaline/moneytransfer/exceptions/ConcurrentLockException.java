package com.themescaline.moneytransfer.exceptions;

import com.themescaline.moneytransfer.util.ExceptionMessage;
import javax.ws.rs.core.Response;

import static com.themescaline.moneytransfer.util.ExceptionMessage.ACCOUNT_LOCKED;

public class ConcurrentLockException extends AppException {
    /**
     * @param accountId account Id
     */
    public ConcurrentLockException(long accountId) {
        super(Response.Status.INTERNAL_SERVER_ERROR, ExceptionMessage.getFormatted(ACCOUNT_LOCKED, String.valueOf(accountId)));
    }
}
