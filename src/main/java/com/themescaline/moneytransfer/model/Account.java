package com.themescaline.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = Account.ALL_SORTED, query = "SELECT a FROM Account a ORDER BY a.id"),
        @NamedQuery(name = Account.CLEAR, query = "DELETE FROM Account a")
})
@Entity
@XmlRootElement
@Table(name = "accounts")
public class Account {

    public static final String ALL_SORTED = "Account.getAllSorted";
    public static final String CLEAR = "Account.clear";

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

