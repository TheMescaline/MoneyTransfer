package com.themescaline.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for transfer operations. Can be stored in DB as transfer operations audit if needed
 *
 * @author lex.korovin@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class TransferInfoPacket {
    Long fromAccountId;
    Long toAccountId;
    Double amount;
}
