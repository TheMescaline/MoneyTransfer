package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.core.Response;

/**
 * Incorrect balance exception
 *
 * @author lex.korovin@gmail.com
 */
public class BalanceException extends AppException {
    /**
     * @param exceptionMessage exception message template or already formatted string
     * @param arguments        arguments for template
     */
    public BalanceException(ExceptionMessage exceptionMessage, String... arguments) {
        super(Response.Status.BAD_REQUEST, ExceptionMessage.getFormatted(exceptionMessage, arguments));
    }
}
