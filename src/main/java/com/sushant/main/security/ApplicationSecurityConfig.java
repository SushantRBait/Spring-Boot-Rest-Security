package com.sushant.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.sushant.main.security.ApplicationUserRole.STUDENT;
import static com.sushant.main.security.ApplicationUserRole.ADMIN;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http  
			.csrf().disable()   //CSRF stands for Cross-Site Request Forgery. It is an attack that forces an end user to execute unwanted actions on a web application in which they are currently authenticated.
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails StudentUser=User.builder()
				.username("Sushant")
				.password(passwordEncoder.encode("password"))
				.roles(STUDENT.name())   //ROLE_STUDENT
				.build();
		
		UserDetails AdminUser=User.builder()
				.username("Ram")
				.password(passwordEncoder.encode("password123"))
				.roles(ADMIN.name())   //ROLE_ADMIN
				.build();
			
		return new InMemoryUserDetailsManager(StudentUser,AdminUser);
	}
}
