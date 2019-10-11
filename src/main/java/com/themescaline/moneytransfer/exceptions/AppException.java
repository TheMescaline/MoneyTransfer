package com.themescaline.moneytransfer.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Application exception
 *
 * @author lex.korovin@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {

    /**
     * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
     * the developer does not have to look into the response headers.
     */
    private int status;

    /**
     * @param status HTTP status of the response
     * @param message message, describing error
     */
    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }
}
