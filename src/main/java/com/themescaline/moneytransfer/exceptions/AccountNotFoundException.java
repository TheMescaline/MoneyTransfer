package com.themescaline.moneytransfer.exceptions;

import com.themescaline.moneytransfer.util.ExceptionMessage;
import javax.ws.rs.core.Response;

public class AccountNotFoundException extends AppException {
    /**
     * @param accountId account id
     */
    public AccountNotFoundException(long accountId) {
        super(Response.Status.NOT_FOUND, ExceptionMessage.getFormatted(ExceptionMessage.NOT_FOUND, String.valueOf(accountId)));
    }
}
