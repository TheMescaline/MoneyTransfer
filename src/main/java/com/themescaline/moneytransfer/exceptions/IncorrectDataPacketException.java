package com.themescaline.moneytransfer.exceptions;

import com.themescaline.moneytransfer.util.ExceptionMessage;
import javax.ws.rs.core.Response;

public class IncorrectDataPacketException extends AppException {
    /**
     * @param exceptionMessage exceptionMessage, describing error
     */
    public IncorrectDataPacketException(ExceptionMessage exceptionMessage) {
        super(Response.Status.BAD_REQUEST, ExceptionMessage.getFormatted(exceptionMessage));
    }
}
