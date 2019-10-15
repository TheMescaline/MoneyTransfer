package com.themescaline.moneytransfer.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

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
     */
    @XmlElement(name = "status")
    private int status;

    /**
     * message describing the error
     */
    @XmlElement(name = "message")
    private String message;

    public ErrorMessage(AppException ex) {
        this.status = ex.getStatus();
        this.message = ex.getMessage();
    }

    public ErrorMessage(Exception ex, int status) {
        this.status = status;
        this.message = ex.getMessage();
    }
}
