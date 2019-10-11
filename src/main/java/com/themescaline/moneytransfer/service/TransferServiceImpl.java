package com.themescaline.moneytransfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import java.text.MessageFormat;

/**
 * Implementation of a service for transferring operations
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@Singleton
public class TransferServiceImpl implements TransferService {
    private final AccountDAO accountDAO;

    @Inject
    public TransferServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void doTransfer(TransferInfoPacket packet) {
        if (packet.getAmount() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Amount of transferred money must not be negative");
        }
        log.info(MessageFormat.format("Processing request of transferring {0} from account ID {1} to account ID {2}", packet.getFromAccountId(), packet.getToAccountId(), packet.getAmount()));
        accountDAO.doTransfer(packet.getFromAccountId(), packet.getToAccountId(), packet.getAmount());
    }
}
