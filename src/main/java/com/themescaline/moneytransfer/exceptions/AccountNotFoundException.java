package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.core.Response;

/**
 * Account not found exception
 *
 * @author lex.korovin@gmail.com
 */
public class AccountNotFoundException extends AppException {
    /**
     * @param accountId account id
     */
    public AccountNotFoundException(long accountId) {
        super(Response.Status.NOT_FOUND, ExceptionMessage.getFormatted(ExceptionMessage.NOT_FOUND, String.valueOf(accountId)));
    }
}
