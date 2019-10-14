package com.themescaline.moneytransfer.exceptions.mappers;

import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.exceptions.ErrorMessage;
import com.themescaline.moneytransfer.exceptions.ExecutionExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class ExecutionExceptionWrapperMapper implements ExceptionMapper<ExecutionExceptionWrapper> {
    @Override
    public Response toResponse(ExecutionExceptionWrapper ex) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        if (ex.getNested().getCause() instanceof AppException) {
            AppException appException = (AppException) ex.getNested().getCause();
            return Response.status(appException.getStatus())
                    .entity(new ErrorMessage(appException))
                    .type(MediaType.APPLICATION_JSON).
                            build();
        }
        log.error(ex.getNested().getMessage(), ex.getNested());

        return Response.status(status)
                .entity(new ErrorMessage(ex, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }
}
