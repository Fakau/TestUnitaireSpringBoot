package com.engine.fakau.TestUnitaire;

import com.engine.fakau.TestUnitaire.domain.BankAccount;
import com.engine.fakau.TestUnitaire.repository.BankAccountRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    @Rollback(value = false)
    @Order(1)
    public void createBankAccountTest(){
        BankAccount bankAccoount = new BankAccount();
        bankAccoount.setAmount(new BigDecimal(200));
        bankAccoount.setBankAccountNo("1233-0003-0928");
        bankAccoount.setOwnerFullName("Jean Louis Liobensky");
        BankAccount bankAccoountSave = bankAccountRepository.save(bankAccoount);
        System.out.println(bankAccoountSave);
        assertNotNull(bankAccoountSave);
    }

    @Test
    @Rollback(value = false)
    @Order(2)
    public void createAllBankAccountTest(){
        List<BankAccount> bankAccounts=new ArrayList<>();
        for(int i=0; i<5; i++){
            BankAccount bankAccoount = new BankAccount();
            bankAccoount.setAmount(new BigDecimal(2001+i));
            bankAccoount.setBankAccountNo("1233-0003-092"+i);
            bankAccoount.setOwnerFullName("Jean Louis Liobensky"+i);
            bankAccounts.add(bankAccoount);
        }
        bankAccounts = bankAccountRepository.saveAll(bankAccounts);
        assertNotNull(bankAccounts);
    }

    @Test
    @Order(3)
    public void getAllBankAccount(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        assertNotNull(bankAccounts);
        System.out.println("\n");
        bankAccounts.forEach(item -> {
            System.out.println("\n"+item.getAmount());
        });
        assertThat(bankAccounts.size()).isEqualTo(6);
    }

    @Test
    @Order(4)
    public void findBankAccountByBankAccountNoTest(){
        Optional<BankAccount> bankAccoountSave = bankAccountRepository.findOneByBankAccountNo("1233-0003-0923");
        assertNotNull(bankAccoountSave.isPresent()?bankAccoountSave.get():null);
    }

    @Test
    @Order(5)
    public void findBankAccountByBankAccountNoNotExistTest(){
        Optional<BankAccount> bankAccoountSave = bankAccountRepository.findOneByBankAccountNo("12330003-0923");
        assertNull(bankAccoountSave.isPresent()?bankAccoountSave.get():null);
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void updateBankAccountTest(){
        Optional<BankAccount> bankAccountOPT = bankAccountRepository.findOneByBankAccountNo("1233-0003-0923");
        assertNotNull(bankAccountOPT.isPresent()?bankAccountOPT.get():null);
        BankAccount bankAccount= bankAccountOPT.get();
        bankAccount.setAmount(new BigDecimal(2005));
        BankAccount bankAccoountUpdate = bankAccountRepository.save(bankAccount);
        assertThat(bankAccoountUpdate.getAmount()).isEqualTo(new BigDecimal(2005));
    }

    @Test
    @Order(7)
    public void findMaxBankAccountAmmout(){
        BigDecimal ammout  = bankAccountRepository.findMaxBankAccountAmmout();
        System.out.println("Max amount :"+ammout);
        assertNotNull(ammout);
        assertThat(ammout).isEqualTo(new BigDecimal(2005).setScale(2));
    }

    @Test
    @Order(8)
    public void findAllBankAccountWithMaxAmmout(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAllBankAccountWithMaxAmmout();
        System.out.println(bankAccounts);
        assertNotNull(bankAccounts);
        assertThat(bankAccounts.size()).isEqualTo(2);
    }
}
