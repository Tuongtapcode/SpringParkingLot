/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.repositories;

import com.nnt.pojo.User;

/**
 *
 * @author ngoct
 */
public interface UserRepository {

    User getUserByUsername(String username);

    User addUser(User u);
}
