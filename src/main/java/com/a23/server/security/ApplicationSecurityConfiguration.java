package com.a23.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.a23.server.auth.ApplicationUserService;

/**
 * 
 * @author pavanpulla
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // This is support for method level Role and Autjorities checks...
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApplicationUserService applicationUserService;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .csrf().disable()
		   .authorizeRequests()
		   .antMatchers("/", "/css/*", "/js/*") //To Exclude few paths from authentication...
		   .permitAll()
//		   .antMatchers("/api/students/*").hasRole(ApplicationUserRole.ADMIN.name())
		   .anyRequest()
		   .authenticated()
		   .and()
		   .formLogin()
		      .loginPage("/login").permitAll()
		      .usernameParameter("username") // same as default.
		      .passwordParameter("password") // same as default.
		      .defaultSuccessUrl("/main", true)
		    .and()
		    .logout()
		        .logoutUrl("/logout")
		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //default url will also be /logout.with this we can decide on Http Method aswell.
		        .logoutSuccessUrl("/login")
		        
		     .and()
		        .httpBasic();
	}

//	@Override
//	@Bean
	/**
	 * Use this method for using in-memory user Authentication...
	 */
	protected UserDetailsService userDetailsService() {
		UserDetails adminUser = User
				.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
//				.roles(ApplicationUserRoles.ADMIN.name())
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails userPavan = User
				.builder()
				.username("pavan")
				.password(passwordEncoder.encode("pavan"))
//				.roles(ApplicationUserRoles.STUDENT.name())
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
				.build();
		
		UserDetails traineeUser = User
				.builder()
				.username("trainee")
				.password(passwordEncoder.encode("trainee"))
//				.roles(ApplicationUserRoles.STUDENT.name())
				.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
				.build();
		//To use in memory user...
		return new InMemoryUserDetailsManager(adminUser,userPavan, traineeUser);
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(applicationUserService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		   .authenticationProvider(daoAuthenticationProvider());
	}	

}
