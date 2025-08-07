/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.pojo.Payment;
import com.nnt.pojo.PaymentRequest;
import com.nnt.repositories.PaymentRepository;
import com.nnt.services.PaymentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngoct
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment pays(List<PaymentRequest> paymentRequest) {
        return this.paymentRepository.pays(paymentRequest);
    }

    @Override
    public List<Payment> getPayments(Map<String, String> params) {
        return this.paymentRepository.getPayments(params);
    }

}
