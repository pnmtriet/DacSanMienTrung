package com.poly;

import com.poly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig extends WebSecurityConfigurerAdapter {
	/*--Mã hóa mật khẩu--*/
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	UserService userService;

	/*--Quản lý dữ liệu người sử dụng--*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	/*--phan quyen su dung va hinh thuc dang nhap--*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CDRP, CORS
		http.csrf().disable().cors().disable();

		// Phân quyền sử dụng
		http.authorizeRequests().anyRequest().permitAll();// anonymous // Không phân quyền gì cả

		// Điều khiển lỗi truy cập không đúng vai trò
		http.exceptionHandling().accessDeniedPage("/auth/access/denied");// [/error]

		// Giao diện đăng nhập
		http.formLogin()
			.loginPage("/auth/login/form")
			.loginProcessingUrl("/auth/login")// [/login]
			.defaultSuccessUrl("/auth/login/success", false).failureUrl("/auth/login/error")
			.usernameParameter("username") // [username]
			.passwordParameter("password");// [password]
		http.rememberMe().rememberMeParameter("remember"); // [remember-me]

		// dang xuat
		http.logout()
			.logoutUrl("/auth/logoff")// [/logout]
			.logoutSuccessUrl("/auth/logoff/success");

	}
}
