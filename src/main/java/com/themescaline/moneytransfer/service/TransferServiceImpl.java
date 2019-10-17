package com.themescaline.moneytransfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.exceptions.IncorrectDataPacketException;
import com.themescaline.moneytransfer.model.DepositInfoPacket;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.model.WithdrawInfoPacket;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.MessageFormat;

import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.NEGATIVE_DEPOSIT;
import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.NEGATIVE_TRANSFER;
import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.NEGATIVE_WITHDRAW;

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
        if (packet.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectDataPacketException(NEGATIVE_TRANSFER);
        }
        log.info(MessageFormat.format("Processing request of transferring {0} from account ID {1} to account ID {2}", packet.getAmount(), packet.getFromAccountId(), packet.getToAccountId()));
        accountDAO.transfer(packet.getFromAccountId(), packet.getToAccountId(), packet.getAmount());
    }

    @Override
    public void withdraw(WithdrawInfoPacket packet) {
        if (packet.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectDataPacketException(NEGATIVE_WITHDRAW);
        }
        log.info(MessageFormat.format("Processing request of withdrawing {0} from account ID {1}", packet.getAmount(), packet.getAccountId()));
        accountDAO.withdraw(packet.getAccountId(), packet.getAmount());
    }

    @Override
    public void deposit(DepositInfoPacket packet) {
        if (packet.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectDataPacketException(NEGATIVE_DEPOSIT);
        }
        log.info(MessageFormat.format("Processing request of depositing {0} to account ID {1}", packet.getAmount(), packet.getAccountId()));
        accountDAO.deposit(packet.getAccountId(), packet.getAmount());
    }
}
