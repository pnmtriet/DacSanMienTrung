package com.poly.dao;


import com.poly.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface AccountDAO extends JpaRepository<Account, String> {
    @Query("SELECT a FROM Account a WHERE email=?1 OR phone=?1")
    Account findByEmailOrPhone(String emailOrPhone);

    @Query("SELECT a FROM Account a WHERE a.userName=?1")
    Account findByUsername(String username);

}
