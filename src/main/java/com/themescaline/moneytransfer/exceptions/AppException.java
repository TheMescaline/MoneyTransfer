package com.themescaline.moneytransfer.exceptions;

import lombok.Data;

/**
 * Application exception
 *
 * @author lex.korovin@gmail.com
 */
@Data
public class AppException extends RuntimeException {

    /**
     * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
     * the developer does not have to look into the response headers.
     *
     * @author lex.korovin@gmail.com
     */
    Integer status;

    /**
     * @param status
     * @param message
     * @author lex.korovin@gmail.com
     */
    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }
}
