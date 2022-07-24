package com.example.security.security;

import static com.example.security.security.ApplicationUserRole.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
// this annotation tells the configuration that we want to use
// the preAuthorize annotations for permission or role based Auth
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
//        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        .and()
//        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/","index","/css/*","/js/*")
        .permitAll()
        //this will protect the api to only be accessed by students
        .antMatchers("/api/**").hasRole(STUDENT.name())
//        .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
//        .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//        .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//        .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
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
//        .roles(STUDENT.name()) //internally will be ROLE_STUDENT
        .authorities(STUDENT.getGrantedAuthorities())
        .build();

    UserDetails ayaUser = User.builder()
        .username("aya")
        .password(passwordEncoder.encode("pass123"))
//        .roles(ADMIN.name()) //ROLE_ADMIN
        .authorities(ADMIN.getGrantedAuthorities())
        .build();

    UserDetails tomUser = User.builder()
        .username("tom")
        .password(passwordEncoder.encode("pass123"))
//        .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
        .authorities(ADMINTRAINEE.getGrantedAuthorities())
        .build();

    return new InMemoryUserDetailsManager(
        manalRzzlUser,
        ayaUser,
        tomUser
    );
//    return super.userDetailsService();
  }

}
