package com.themescaline.moneytransfer.exceptions.mappers;

import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.exceptions.ErrorMessage;
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
    @Override
    public Response toResponse(AppException ex) {
        return Response.status(ex.getStatus())
                .entity(new ErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }
}
