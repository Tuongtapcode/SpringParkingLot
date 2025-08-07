/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.Payment;
import com.nnt.pojo.PaymentRequest;
import com.nnt.services.PaymentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngoct
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/secure/pays")
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> getPayments(@RequestParam Map<String, String> params) {
        return this.paymentService.getPayments(params);
    }

    @PostMapping("/secure/pays")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody List<PaymentRequest> paymentRequests) {
        return this.paymentService.pays(paymentRequests);
    }
}
