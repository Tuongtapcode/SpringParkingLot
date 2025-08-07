/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngoct
 */
@Entity
@Table(name = "reservation")
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
    @NamedQuery(name = "Reservation.findByStartTime", query = "SELECT r FROM Reservation r WHERE r.startTime = :startTime"),
    @NamedQuery(name = "Reservation.findByEndTime", query = "SELECT r FROM Reservation r WHERE r.endTime = :endTime"),
    @NamedQuery(name = "Reservation.findByActualStartTime", query = "SELECT r FROM Reservation r WHERE r.actualStartTime = :actualStartTime"),
    @NamedQuery(name = "Reservation.findByActualEndTime", query = "SELECT r FROM Reservation r WHERE r.actualEndTime = :actualEndTime"),
    @NamedQuery(name = "Reservation.findByStatus", query = "SELECT r FROM Reservation r WHERE r.status = :status"),
    @NamedQuery(name = "Reservation.findByCreatedAt", query = "SELECT r FROM Reservation r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "Reservation.findByUpdatedAt", query = "SELECT r FROM Reservation r WHERE r.updatedAt = :updatedAt"),
    @NamedQuery(name = "Reservation.findByBaseAmount", query = "SELECT r FROM Reservation r WHERE r.baseAmount = :baseAmount"),
    @NamedQuery(name = "Reservation.findByExtraFee", query = "SELECT r FROM Reservation r WHERE r.extraFee = :extraFee"),
    @NamedQuery(name = "Reservation.findByDiscount", query = "SELECT r FROM Reservation r WHERE r.discount = :discount")})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "actual_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualStartTime;
    @Column(name = "actual_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualEndTime;
    @Size(max = 9)
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "base_amount")
    private double baseAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "extra_fee")
    private double extraFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "discount")
    private double discount;
    @JoinColumn(name = "parking_space_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ParkingSpace parkingSpaceId;
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Payment paymentId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @ManyToOne
    private Vehicle vehicleId;

    public Reservation() {
    }

    public Reservation(Integer id) {
        this.id = id;
    }

    public Reservation(Integer id, double baseAmount, double extraFee, double discount) {
        this.id = id;
        this.baseAmount = baseAmount;
        this.extraFee = extraFee;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(double extraFee) {
        this.extraFee = extraFee;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ParkingSpace getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(ParkingSpace parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payment paymentId) {
        this.paymentId = paymentId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nnt.pojo.Reservation[ id=" + id + " ]";
    }

}
