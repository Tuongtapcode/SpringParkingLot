/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.pojo;

/**
 *
 * @author ngoct
 */
public class PaymentRequest {

    private Integer reservationId;
    private String method = "VNPAY";

    /**
     * @return the reservationId
     */
    public Integer getReservationId() {
        return reservationId;
    }

    /**
     * @param reservationId the reservationId to set
     */
    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

}
