package com.poly.dao;

import com.poly.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleDAO extends JpaRepository<Role, Integer> {
}
