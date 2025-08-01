/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author ngoct
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.nnt.controllers",
    "com.nnt.repositories",
    "com.nnt.services"
})
public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                // Cho phép truy cập tĩnh
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                // API cho phép GET, nhưng POST cần xác thực
                .requestMatchers("/api/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/**").authenticated()
                // Các trang cần đăng nhập
                .requestMatchers("/", "/parkinglots/**", "/parkingspaces/**", "/extensions/**", "/stats").hasRole("ADMIN") // cần quyền ADMIN
                .anyRequest().permitAll()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
                );
        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "duqln52pu",
                        "api_key", "924291448136996",
                        "api_secret", "JdsaVdMKR25NG7w-VRIBB7H2WXk",
                        "secure", true));
        return cloudinary;
    }
}
