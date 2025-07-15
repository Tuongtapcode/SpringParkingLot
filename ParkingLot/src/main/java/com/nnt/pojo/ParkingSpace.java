/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.pojo;

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
@Table(name = "parking_space")
@NamedQueries({
    @NamedQuery(name = "ParkingSpace.findAll", query = "SELECT p FROM ParkingSpace p"),
    @NamedQuery(name = "ParkingSpace.findById", query = "SELECT p FROM ParkingSpace p WHERE p.id = :id"),
    @NamedQuery(name = "ParkingSpace.findByName", query = "SELECT p FROM ParkingSpace p WHERE p.name = :name"),
    @NamedQuery(name = "ParkingSpace.findByStatus", query = "SELECT p FROM ParkingSpace p WHERE p.status = :status"),
    @NamedQuery(name = "ParkingSpace.findByIsActive", query = "SELECT p FROM ParkingSpace p WHERE p.isActive = :isActive"),
    @NamedQuery(name = "ParkingSpace.findByCreatedAt", query = "SELECT p FROM ParkingSpace p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "ParkingSpace.findByUpdatedAt", query = "SELECT p FROM ParkingSpace p WHERE p.updatedAt = :updatedAt")})
public class ParkingSpace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 9)
    @Column(name = "status")
    private String status;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "parking_lot_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ParkingLot parkingLotId;

    public ParkingSpace() {
    }

    public ParkingSpace(Integer id) {
        this.id = id;
    }

    public ParkingSpace(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public ParkingLot getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(ParkingLot parkingLotId) {
        this.parkingLotId = parkingLotId;
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
        if (!(object instanceof ParkingSpace)) {
            return false;
        }
        ParkingSpace other = (ParkingSpace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nnt.pojo.ParkingSpace[ id=" + id + " ]";
    }
    
}
