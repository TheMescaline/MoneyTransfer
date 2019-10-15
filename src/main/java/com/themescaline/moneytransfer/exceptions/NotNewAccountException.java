package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.core.Response;

import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.NOT_NEW_ACCOUNT;

/**
 * Exception for saving not new accounts
 *
 * @author lex.korovin@gmail.com
 */
public class NotNewAccountException extends AppException {

    public NotNewAccountException() {
        super(Response.Status.BAD_REQUEST, ExceptionMessage.getFormatted(NOT_NEW_ACCOUNT));
    }
}
