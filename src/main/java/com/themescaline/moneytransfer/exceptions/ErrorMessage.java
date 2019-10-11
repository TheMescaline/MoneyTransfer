package com.themescaline.moneytransfer.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Adapter exception for other exceptions
 *
 * @author lex.korovin@gmail.com
 */
@Data
@NoArgsConstructor
@XmlRootElement
public class ErrorMessage {

    /**
     * contains the same HTTP Status code returned by the server
     *
     * @author lex.korovin@gmail.com
     */
    @XmlElement(name = "status")
    int status;

    /**
     * message describing the error
     *
     * @author lex.korovin@gmail.com
     */
    @XmlElement(name = "message")
    String message;

    public ErrorMessage(AppException ex) {
        this.status = ex.getStatus();
        this.message = ex.getMessage();
    }

    public ErrorMessage(NotFoundException ex) {
        this.status = Response.Status.NOT_FOUND.getStatusCode();
        this.message = ex.getMessage();
    }
}
