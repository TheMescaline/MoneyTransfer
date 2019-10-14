package com.themescaline.moneytransfer.exceptions;

import com.themescaline.moneytransfer.util.ExceptionMessage;
import javax.ws.rs.core.Response;

import static com.themescaline.moneytransfer.util.ExceptionMessage.NOT_NEW_ACCOUNT;

public class NotNewAccountException extends AppException {

    public NotNewAccountException() {
        super(Response.Status.BAD_REQUEST, ExceptionMessage.getFormatted(NOT_NEW_ACCOUNT));
    }
}
