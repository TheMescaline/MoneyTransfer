package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.core.Response;

public class UncategorizedException extends AppException {
    /**
     * @param message message, describing error
     */
    public UncategorizedException(String message) {
        super(Response.Status.INTERNAL_SERVER_ERROR, message);
    }
}
