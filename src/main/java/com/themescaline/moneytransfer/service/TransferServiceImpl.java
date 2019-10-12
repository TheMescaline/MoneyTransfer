package com.themescaline.moneytransfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.DepositInfoPacket;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.model.WithdrawInfoPacket;
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
    public void transfer(TransferInfoPacket packet) {
        if (packet.getAmount() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Amount of transferring money must not be negative");
        }
        log.info(MessageFormat.format("Processing request of transferring {0} from account ID {1} to account ID {2}", packet.getAmount(), packet.getFromAccountId(), packet.getToAccountId()));
        accountDAO.transfer(packet.getFromAccountId(), packet.getToAccountId(), packet.getAmount());
    }

    @Override
    public void withdraw(WithdrawInfoPacket packet) {
        if (packet.getAmount() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Amount of withdrawing money must not be negative");
        }
        log.info(MessageFormat.format("Processing request of withdrawing {0} from account ID {1}", packet.getAmount(), packet.getAccountId()));
        accountDAO.withdraw(packet.getAccountId(), packet.getAmount());
    }

    @Override
    public void deposit(DepositInfoPacket packet) {
        if (packet.getAmount() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Amount of depositing money must not be negative");
        }
        log.info(MessageFormat.format("Processing request of depositing {0} to account ID {1}", packet.getAmount(), packet.getAccountId()));
        accountDAO.deposit(packet.getAccountId(), packet.getAmount());
    }
}
