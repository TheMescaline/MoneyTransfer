package com.themescaline.moneytransfer.web;

import com.themescaline.moneytransfer.config.BeanHelper;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.service.TransferService;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/transfer")
public class MoneyTransferServlet extends HttpServlet {
    private final TransferService transferService = BeanHelper.getBean(TransferService.class);

    @PUT
    @Consumes("application/json")
    public Response doTransfer(TransferInfoPacket packet) {
        Response response;
        boolean transferResult = transferService.doTransfer(packet);
        if (!transferResult) {
            response = Response.notModified().build();
        } else {
            response = Response.ok().build();
        }
        return response;
    }
}
