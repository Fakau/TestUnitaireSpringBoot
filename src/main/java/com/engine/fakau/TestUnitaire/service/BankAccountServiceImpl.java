package com.engine.fakau.TestUnitaire.service;

import com.engine.fakau.TestUnitaire.domain.BankAccount;
import com.engine.fakau.TestUnitaire.exception.BadRequestException;
import com.engine.fakau.TestUnitaire.exception.BankAccountNotFoundException;
import com.engine.fakau.TestUnitaire.exception.InvalidAmountException;
import com.engine.fakau.TestUnitaire.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BankAccountServiceImpl implements IBankAccountService {
    @Autowired
    private BankAccountRepository repository;


    @Override
    public BankAccount save(BankAccount bankAccount) {
        if(bankAccount.getAmount().doubleValue() <= 0){
            throw new BadRequestException("Cannot add or update bankAccount amount less than zero");
        }
        return repository.save(bankAccount);
    }

    @Override
    public BankAccount deposit(String bankAccountNo, BigDecimal amount) {
        if(amount == null || amount.doubleValue() <= 0){
            throw new InvalidAmountException("Cannot make a deposit with amount null or  less than zero");
        }
        BankAccount bankAccount = repository.findOneByBankAccountNo(bankAccountNo).orElseThrow(()
                -> new BankAccountNotFoundException("Cannot find bankAccount with bankAccountNo "+bankAccountNo));
        bankAccount.setAmount(bankAccount.getAmount().add(amount));
        return repository.save(bankAccount);
    }

    @Override
    public BankAccount withdraw(String bankAccountNo, BigDecimal amount) {
        if(amount == null || amount.doubleValue() <= 0){
            throw new InvalidAmountException("Cannot make a withdraw with amount null or  less than zero");
        }
        BankAccount bankAccount = repository.findOneByBankAccountNo(bankAccountNo).orElseThrow(()
                -> new BankAccountNotFoundException("Cannot find bankAccount with bankAccountNo "+bankAccountNo));
        if(amount.doubleValue()  >= bankAccount.getAmount().doubleValue()){
            throw new InvalidAmountException("Your don't have enougth money");
        }
        bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
        return repository.save(bankAccount);
    }

    @Override
    public List<BankAccount> saveAll(List<BankAccount> bankAccounts) {
        bankAccounts.forEach(item -> {
            if(item.getAmount().doubleValue() <= 0){
                throw new BadRequestException("Cannot add or update bankAccount amout less than zero");
            }
        });
        return repository.saveAll(bankAccounts);
    }

    @Override
    public Optional<BankAccount> findOneByBankAccountNo(String bankAccountNo) {
        return repository.findOneByBankAccountNo(bankAccountNo);
    }

    @Override
    public List<BankAccount> findAllBankAccountWithMaxAmmout() {
        return repository.findAllBankAccountWithMaxAmmout();
    }

    @Override
    public BigDecimal findMaxBankAccountAmmout() {
        return repository.findMaxBankAccountAmmout();
    }

    @Override
    public List<BankAccount> findAll() {
        return repository.findAll();
    }
}
