package com.rova.accountService.repository;

import com.rova.accountService.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByCustomerId(String id);
}
