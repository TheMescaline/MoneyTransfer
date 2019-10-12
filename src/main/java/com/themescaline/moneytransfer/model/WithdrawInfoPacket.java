package com.themescaline.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for withdraw operations. Can be stored in DB as transfer operations audit if needed
 *
 * @author lex.korovin@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class WithdrawInfoPacket {
    private Long accountId;
    private Double amount;
}
