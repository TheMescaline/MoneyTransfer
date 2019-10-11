package com.themescaline.moneytransfer.service;

import com.google.inject.ImplementedBy;
import com.themescaline.moneytransfer.model.TransferInfoPacket;

@ImplementedBy(TransferServiceImpl.class)
public interface TransferService {
    void doTransfer(TransferInfoPacket packet);
}
