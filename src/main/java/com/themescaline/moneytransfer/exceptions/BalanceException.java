package com.themescaline.moneytransfer.exceptions;

import com.themescaline.moneytransfer.util.ExceptionMessage;
import javax.ws.rs.core.Response;

public class BalanceException extends AppException{
    /**
     * @param exceptionMessage exception message template or already formatted string
     * @param arguments arguments for template
     */
    public BalanceException(ExceptionMessage exceptionMessage, String... arguments) {
        super(Response.Status.BAD_REQUEST, ExceptionMessage.getFormatted(exceptionMessage, arguments));
    }
}
