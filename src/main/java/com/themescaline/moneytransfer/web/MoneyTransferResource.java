package com.themescaline.moneytransfer.web;

import com.themescaline.moneytransfer.model.DepositInfoPacket;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.model.WithdrawInfoPacket;
import com.themescaline.moneytransfer.service.TransferService;
import com.themescaline.moneytransfer.util.BeanHelper;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Resource for transfer operations
 *
 * @author lex.korovin@gmail.com
 */
@Path("v1/open")
public class MoneyTransferResource {
    private final TransferService transferService = BeanHelper.getBean(TransferService.class);

    @PUT
    @Path("transfer")
    @Consumes("application/json")
    public Response doTransfer(TransferInfoPacket packet) {
        transferService.transfer(packet);
        return Response.ok().build();
    }

    @PUT
    @Path("withdraw")
    @Consumes("application/json")
    public Response doWithdraw(WithdrawInfoPacket packet) {
        transferService.withdraw(packet);
        return Response.ok().build();
    }

    @PUT
    @Path("deposit")
    @Consumes("application/json")
    public Response doDeposit(DepositInfoPacket packet) {
        transferService.deposit(packet);
        return Response.ok().build();
    }
}
