package com.engine.fakau.TestUnitaire.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_accoount")
public class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bank_account_no", nullable = false)
    private String bankAccountNo;
    @Column(name = "owner_full_name", nullable = false)
    private String ownerFullName;
    @Column(name = "amount", nullable = false, precision = 21, scale = 2)
    private BigDecimal amount;
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BankAccoount{" +
                "id=" + id +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", ownerFullName='" + ownerFullName + '\'' +
                ", amount=" + amount +
                ", version=" + version +
                '}';
    }
}
