package com.engine.fakau.TestUnitaire.repository;

import com.engine.fakau.TestUnitaire.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    Optional<BankAccount> findOneByBankAccountNo(String bankAccountNo);

    @Query("SELECT B FROM BankAccount B WHERE B.amount =(" +
            "SELECT max(BS.amount) FROM BankAccount BS ) ")
    List<BankAccount> findAllBankAccountWithMaxAmmout();

    @Query("SELECT max(BS.amount) FROM BankAccount BS")
    BigDecimal findMaxBankAccountAmmout();
}
