package com.themescaline.moneytransfer.web;

import com.themescaline.moneytransfer.config.BeanHelper;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.service.AccountService;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/v1/accounts")
public class AccountsServlet extends HttpServlet {
    private final AccountService accountService = BeanHelper.getBean(AccountService.class);

    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(accountService.getAll()).build();
    }

    @Path("{accountId}")
    @GET
    @Produces("application/json")
    public Response getOne(@PathParam("accountId") Long accountId) {
        Account result = accountService.getOne(accountId);
        return result != null ?
                Response.ok(result).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response save(Account account) {
        Account saved = accountService.save(account);
        return saved != null ?
                Response.status(Response.Status.CREATED).entity(saved).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("{accountId}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("accountId") long accountId, Account account) {
        Account updated = accountService.update(accountId, account);
        return updated != null ?
                Response.status(Response.Status.CREATED).entity(updated).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("{accountId}")
    @DELETE
    public Response delete(@PathParam("accountId") long accountId) {
        return accountService.delete(accountId) ?
                Response.noContent().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
