package com.themescaline.moneytransfer.web;

import com.themescaline.moneytransfer.config.BeanHelper;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.service.TransferService;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/v1/transfer")
public class MoneyTransferServlet {
    private final TransferService transferService = BeanHelper.getBean(TransferService.class);

    @PUT
    @Consumes("application/json")
    public Response doTransfer(TransferInfoPacket packet) {
        return transferService.doTransfer(packet) ?
                Response.ok().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
