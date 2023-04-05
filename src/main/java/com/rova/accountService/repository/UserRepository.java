package com.rova.accountService.repository;

import com.rova.accountService.model.Account;
import com.rova.accountService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
