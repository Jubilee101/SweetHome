package com.hzhang.sweethome.config;

import com.hzhang.sweethome.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import javax.sql.DataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register/*").permitAll()
                .antMatchers(HttpMethod.POST, "/authenticate/*").permitAll()
                .antMatchers(HttpMethod.POST,"/public_invoice").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.POST,"/public_invoice/*").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.GET,"/public_invoice").permitAll()
                .antMatchers(HttpMethod.GET,"/public_invoice/*").permitAll()
                .antMatchers(HttpMethod.POST,"/personal_invoice").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.POST,"/personal_invoice/*").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.GET,"/personal_invoice").hasAuthority("ROLE_RESIDENT")
                .antMatchers(HttpMethod.GET,"/personal_invoice/*").hasAuthority("ROLE_RESIDENT")
                .antMatchers(HttpMethod.POST,"/due").hasAuthority("ROLE_RESIDENT")
                .antMatchers(HttpMethod.POST,"/due/*").hasAuthority("ROLE_RESIDENT")
                .antMatchers(HttpMethod.GET, "/unread_nums/*").permitAll()
                .antMatchers(HttpMethod.GET, "/unread_nums").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT email, password, true FROM user WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email, authority FROM authority WHERE email = ?");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
