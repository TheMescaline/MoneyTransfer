package com.themescaline.moneytransfer.exceptions;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper
 *
 * @author lex.korovin@gmail.com
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

    public Response toResponse(AppException ex) {
        return Response.status(ex.getStatus())
                .entity(new ErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }

    public Response toResponse(NotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }
}
