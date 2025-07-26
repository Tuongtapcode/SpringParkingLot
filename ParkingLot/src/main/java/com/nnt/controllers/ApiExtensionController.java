/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.services.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngoct
 */
@RestController // RESTful web controller — tức là nó sẽ trả dữ liệu (thường là JSON/XML)
@RequestMapping("/api") // Tạo prefix chung cho tất cả method bên trong class.
@CrossOrigin //Cho phép truy cập từ nguồn khác 
public class ApiExtensionController {

    @Autowired
    private ExtensionService extensionService;

    @DeleteMapping("/extensions/{extensionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExtension(@PathVariable(value = "extensionId") int id) {
        this.extensionService.deleteExtension(id);
    }
}
