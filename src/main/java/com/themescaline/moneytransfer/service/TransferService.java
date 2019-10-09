package com.themescaline.moneytransfer.service;

import com.google.inject.ImplementedBy;
import com.themescaline.moneytransfer.model.TransferInfoPacket;

@ImplementedBy(TransferServiceImpl.class)
public interface TransferService {
    boolean doTransfer(TransferInfoPacket packet);
}
