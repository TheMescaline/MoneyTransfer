package com.themescaline.moneytransfer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement
public class TransferInfoPacket {
    Long fromAccount;
    Long toAccount;
    Double amount;
}
