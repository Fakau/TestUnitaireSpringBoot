package com.engine.fakau.TestUnitaire.repository;

import com.engine.fakau.TestUnitaire.domain.BankAccoount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccoount,Long> {
}
