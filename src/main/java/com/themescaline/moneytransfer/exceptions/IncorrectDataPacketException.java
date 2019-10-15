package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.core.Response;

/**
 * Exception or incorrect data inside DataPackets
 *
 * @author lex.korovin@gmail.com
 */
public class IncorrectDataPacketException extends AppException {
    /**
     * @param exceptionMessage exceptionMessage, describing error
     */
    public IncorrectDataPacketException(ExceptionMessage exceptionMessage) {
        super(Response.Status.BAD_REQUEST, ExceptionMessage.getFormatted(exceptionMessage));
    }
}
