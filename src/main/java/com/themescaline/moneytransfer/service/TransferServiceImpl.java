package com.themescaline.moneytransfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
@Singleton
public class TransferServiceImpl implements TransferService {
    private final AccountDAO accountDAO;

    @Inject
    public TransferServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public boolean doTransfer(TransferInfoPacket packet) {
        log.info(MessageFormat.format("Transferring {0} from account ID {1} to account ID {2}", packet.getFromAccountId(), packet.getToAccountId(), packet.getAmount()));
        return accountDAO.doTransfer(packet.getFromAccountId(), packet.getToAccountId(), packet.getAmount());
    }
}
