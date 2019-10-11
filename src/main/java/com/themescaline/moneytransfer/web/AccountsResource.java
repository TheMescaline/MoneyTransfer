package com.themescaline.moneytransfer.web;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.themescaline.moneytransfer.config.BeanHelper;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.service.AccountService;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.annotation.JacksonFeatures;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/accounts")
public class AccountsResource {
    private final AccountService accountService = BeanHelper.getBean(AccountService.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getAll() {
        return Response.ok(accountService.getAll()).build();
    }

    @Path("{accountId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getOne(@PathParam("accountId") Long accountId) {
        return Response.ok(accountService.getOne(accountId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response save(Account account) {
        return Response.status(Response.Status.CREATED).entity(accountService.save(account)).build();
    }

    @Path("{accountId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response update(@PathParam("accountId") long accountId, Account account) {
        return Response.ok(accountService.update(accountId, account)).build();
    }

    @Path("{accountId}")
    @DELETE
    public Response delete(@PathParam("accountId") long accountId) {
        accountService.delete(accountId);
        return Response.noContent().build();
    }
}
