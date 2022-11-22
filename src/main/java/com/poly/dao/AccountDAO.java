package com.poly.dao;


import com.poly.entity.Account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountDAO extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE email=?1 OR phone=?1")
    Account findByEmailOrPhone(String emailOrPhone);

    @Query("SELECT a FROM Account a WHERE a.userName=?1")
    Account findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.userName=?1 OR a.fullName like %?1%")
    List<Account> findBUsernameOrFullName(String key);

    @Query("SELECT a FROM Account a WHERE a.userName=?1 OR a.fullName like %?1%")
    Page<Account> findBUsernameOrFullName(Pageable page,String key);
}
