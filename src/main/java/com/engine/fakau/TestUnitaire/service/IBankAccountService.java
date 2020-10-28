package com.engine.fakau.TestUnitaire.service;

import com.engine.fakau.TestUnitaire.domain.BankAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IBankAccountService {
    BankAccount save(BankAccount bankAccount);
    BankAccount deposit(final String bankAccountNo, final BigDecimal amount);
    BankAccount withdraw(final String bankAccountNo, final BigDecimal amount);
    List<BankAccount> saveAll(List<BankAccount> bankAccounts);
    Optional<BankAccount> findOneByBankAccountNo(String bankAccountNo);
    List<BankAccount> findAllBankAccountWithMaxAmmout();
    BigDecimal findMaxBankAccountAmmout();
    List<BankAccount> findAll();


}
