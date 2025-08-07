/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.pojo;

import java.util.Date;

/**
 *
 * @author ngoct
 */
public class ReservationRequest {

    private Integer parkingSpaceId;
    private Date startTime;
    private Date endTime;
    private String licensePlate;

    /**
     * @return the parkingSpaceId
     */
    public Integer getParkingSpaceId() {
        return parkingSpaceId;
    }

    /**
     * @param parkingSpaceId the parkingSpaceId to set
     */
    public void setParkingSpaceId(Integer parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @param licensePlate the licensePlate to set
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
