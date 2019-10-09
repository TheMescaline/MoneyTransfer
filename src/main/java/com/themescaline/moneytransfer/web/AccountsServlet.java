package com.themescaline.moneytransfer.web;

import com.themescaline.moneytransfer.config.BeanHelper;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.service.AccountService;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/accounts")
public class AccountsServlet extends HttpServlet {
    private final AccountService accountService = BeanHelper.getBean(AccountService.class);

    @GET
    @Produces("application/json")
    public Response getAll() {
        Response response;
        List<Account> result = accountService.getAll();
        if (!result.isEmpty()) {
            response = Response.ok(result).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @Path("{accountId}")
    @GET
    @Produces("application/json")
    public Response getOne(@PathParam("accountId") Long accountId) {
        Response response;
        Account result = accountService.getOne(accountId);
        if (result != null) {
            response = Response.ok(result).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response save(Account account) {
        Response response;
        Account result = accountService.save(account);
        if (result != null) {
            response = Response.status(Response.Status.CREATED).entity(result).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @Path("{accountId}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("accountId") long accountId, Account account) {
        Response result;
        Account res = accountService.update(accountId, account);
        if (res != null) {
            result = Response.status(Response.Status.CREATED).entity(res).build();
        } else {
            result = Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return result;
    }

    @Path("{accountId}")
    @DELETE
    public Response delete(@PathParam("accountId") long accountId) {
        return getResponse(accountService.delete(accountId));
    }

    private Response getResponse(boolean isSuccessful) {
        Response response;
        if (isSuccessful) {
            response = Response.noContent().build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}
