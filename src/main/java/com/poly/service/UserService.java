package com.poly.service;

import com.poly.dao.AccountDAO;
import com.poly.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	AccountDAO userDAO;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Account user=userDAO.findByUsername(username);
			String password=user.getPassWord();
			String role=String.valueOf(user.getRole().getId());
			var a=User.withUsername(username)
					.password(pe.encode(password))
					.roles(role).build();
			return User.withUsername(username)
					.password(pe.encode(password))
					.roles(role).build();
		} catch (Exception e) {
			throw new UsernameNotFoundException(username+"not found");
		}
	}
	
}
