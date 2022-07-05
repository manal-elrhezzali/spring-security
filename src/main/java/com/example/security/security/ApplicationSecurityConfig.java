package com.example.security.security;

import static com.example.security.security.ApplicationUserRole.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ApplicationSecurityConfig(
      PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    super.configure(http);
    http
        .authorizeRequests()
        .antMatchers("/","index","/css/*","/js/*")
        .permitAll()
        //this will protect the api to only be accessed by students
        .antMatchers("/api/**").hasRole(STUDENT.name())
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic();
  }

  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    UserDetails manalRzzlUser = User.builder()
        .username("manalrzzl")
        .password(passwordEncoder.encode("password"))
        .roles(STUDENT.name()) //internally will be ROLE_STUDENT
        .build();

    UserDetails ayaUser = User.builder()
        .username("aya")
        .password(passwordEncoder.encode("pass123"))
        .roles(ADMIN.name())
        .build();

    return new InMemoryUserDetailsManager(
        manalRzzlUser,
        ayaUser
    );
//    return super.userDetailsService();
  }

}
