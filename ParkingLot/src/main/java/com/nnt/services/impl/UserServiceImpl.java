/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nnt.pojo.User;
import com.nnt.repositories.UserRepository;
import com.nnt.services.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ngoct
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setUsername(params.get("username"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setRole("ROLE_USER");

        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.userRepository.addUser(u);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepository.authenticate(username, password);
    }

    public User updateUser(Map<String, String> params, MultipartFile avatar, int userId) {

        User existingUser = this.userRepository.getUserById(userId);

        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        String currentPassword = params.get("currentPassword");
        if (!passwordEncoder.matches(currentPassword, existingUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mật khẩu hiện tại không đúng!");
        }
        if (params.containsKey("firstName")) {
            existingUser.setFirstName(params.get("firstName"));
        }

        if (params.containsKey("lastName")) {
            existingUser.setLastName(params.get("lastName"));
        }

        if (params.containsKey("email")) {
            existingUser.setEmail(params.get("email"));
        }

        if (params.containsKey("phone")) {
            existingUser.setPhone(params.get("phone"));
        }

        if (params.containsKey("username")) {
            existingUser.setUsername(params.get("username"));
        }

        if (params.containsKey("password") && !params.get("password").isBlank()) {
            existingUser.setPassword(this.passwordEncoder.encode(params.get("password")));
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                existingUser.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE,
                        "Error uploading avatar", ex);
            }
        }

        return this.userRepository.updateUser(existingUser);
    }
}
