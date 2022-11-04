package com.poly.dao;


import com.poly.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, String> {

}
