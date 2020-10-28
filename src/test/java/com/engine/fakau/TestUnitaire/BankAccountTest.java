package com.engine.fakau.TestUnitaire;

import com.engine.fakau.TestUnitaire.domain.BankAccount;
import com.engine.fakau.TestUnitaire.exception.InvalidAmountException;
import com.engine.fakau.TestUnitaire.service.BankAccountServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

// @DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestUnitaireApplication.class)
public class BankAccountTest {

    @Autowired
    private BankAccountServiceImpl bankAccountService;


    @Test
    @Rollback(value = false)
    @Order(1)
    public void createBankAccountTest(){
        BankAccount bankAccoount = new BankAccount();
        bankAccoount.setAmount(new BigDecimal(200));
        bankAccoount.setBankAccountNo("1233-0003-0928");
        bankAccoount.setOwnerFullName("Jean Louis Liobensky");
        BankAccount bankAccoountSave = bankAccountService.save(bankAccoount);
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
        bankAccounts = bankAccountService.saveAll(bankAccounts);
        assertNotNull(bankAccounts);
    }

    @Test
    @Order(3)
    public void getAllBankAccountTest(){
        List<BankAccount> bankAccounts = bankAccountService.findAll();
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
        Optional<BankAccount> bankAccoountSave = bankAccountService.findOneByBankAccountNo("1233-0003-0923");
        assertNotNull(bankAccoountSave.isPresent()?bankAccoountSave.get():null);
    }

    @Test
    @Order(5)
    public void findBankAccountByBankAccountNoNotExistTest(){
        Optional<BankAccount> bankAccoountSave = bankAccountService.findOneByBankAccountNo("12330003-0923");
        assertNull(bankAccoountSave.isPresent()?bankAccoountSave.get():null);
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void updateBankAccountTest(){
        Optional<BankAccount> bankAccountOPT = bankAccountService.findOneByBankAccountNo("1233-0003-0923");
        assertNotNull(bankAccountOPT.isPresent()?bankAccountOPT.get():null);
        BankAccount bankAccount= bankAccountOPT.get();
        bankAccount.setAmount(new BigDecimal(2005));
        BankAccount bankAccoountUpdate = bankAccountService.save(bankAccount);
        assertThat(bankAccoountUpdate.getAmount()).isEqualTo(new BigDecimal(2005));
    }

    @Test
    @Order(7)
    public void findMaxBankAccountAmmoutTest(){
        BigDecimal ammout  = bankAccountService.findMaxBankAccountAmmout();
        System.out.println("Max amount :"+ammout);
        assertNotNull(ammout);
        assertThat(ammout).isEqualTo(new BigDecimal(2005).setScale(2));
    }

    @Test
    @Order(8)
    public void findAllBankAccountWithMaxAmmoutTest(){
        List<BankAccount> bankAccounts = bankAccountService.findAllBankAccountWithMaxAmmout();
        System.out.println(bankAccounts);
        assertNotNull(bankAccounts);
        assertThat(bankAccounts.size()).isEqualTo(2);
    }

    @Test
    @Order(9)
    @Rollback(value = false)
    public void depositTest(){
        BankAccount bankAccounts = bankAccountService.deposit("1233-0003-0928", new BigDecimal(1000));
        assertNotNull(bankAccounts);
        assertThat(bankAccounts.getAmount()).isEqualTo(new BigDecimal(1200).setScale(2));
    }
    @Test
    @Order(10)
    @Rollback(value = false)
    public void depositWithNegativeAmountTest(){
        BankAccount bankAccounts = null;
        try {
            bankAccounts = bankAccountService.deposit("1233-0003-0928", new BigDecimal(-1000));
        }catch (InvalidAmountException ex){
            System.out.println(ex.getMessage());
        }finally {
            assertNull(bankAccounts);
        }

    }

    @Order(11)
    @Rollback(value = false)
    public void withdraw(){
        BankAccount bankAccounts = bankAccountService.withdraw("1233-0003-0928", new BigDecimal(200));
        assertNotNull(bankAccounts);
        assertThat(bankAccounts.getAmount()).isEqualTo(new BigDecimal(100).setScale(2));
    }

    @Test
    @Order(12)
    @Rollback(value = false)
    public void withdrawNegativeAmountTest(){
        BankAccount bankAccounts = null;
        try {
            bankAccounts = bankAccountService.withdraw("1233-0003-0928", new BigDecimal(-1000));
        }catch (InvalidAmountException ex){
            System.out.println(ex.getMessage());
        }finally {
            assertNull(bankAccounts);
        }

    }
}
