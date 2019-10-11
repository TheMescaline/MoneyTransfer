package com.themescaline.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class TransferInfoPacket {
    Long fromAccountId;
    Long toAccountId;
    Double amount;
}
