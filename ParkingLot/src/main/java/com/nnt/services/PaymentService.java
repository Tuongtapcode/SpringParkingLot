/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import com.nnt.pojo.Payment;
import com.nnt.pojo.PaymentRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ngoct
 */
public interface PaymentService {

    Payment pays(List<PaymentRequest> paymentRequest);

    List<Payment> getPayments(Map<String, String> params);
}
