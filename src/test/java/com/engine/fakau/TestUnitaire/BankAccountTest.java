package com.engine.fakau.TestUnitaire;

import com.engine.fakau.TestUnitaire.domain.BankAccoount;
import com.engine.fakau.TestUnitaire.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

@DataJpaTest
public class BankAccountTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void createBankAccountTest(){
        BankAccoount bankAccoount = new BankAccoount();
        bankAccoount.setAmount(new BigDecimal(200));
        bankAccoount.setBankAccountNo("1233-0003-0923");
        bankAccoount.setOwnerFullName("Jean Louis Liobensky");
        bankAccountRepository.save(bankAccoount);
    }
}
