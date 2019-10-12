package com.themescaline.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Main entity - bank account
 *
 * @author lex.korovin@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    public Account(Double balance) {
        this.balance = balance;
    }

    //cloning constructor
    public Account(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
    }

    @XmlTransient
    public boolean isNew() {
        return id == null;
    }
}

