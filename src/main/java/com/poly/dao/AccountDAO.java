package com.poly.dao;


import com.poly.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccountDAO extends JpaRepository<Account, String> {

}
