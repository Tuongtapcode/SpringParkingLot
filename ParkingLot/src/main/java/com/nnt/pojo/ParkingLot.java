/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author ngoct
 */
@Entity
@Table(name = "parking_lot")
@NamedQueries({
    @NamedQuery(name = "ParkingLot.findAll", query = "SELECT p FROM ParkingLot p"),
    @NamedQuery(name = "ParkingLot.findById", query = "SELECT p FROM ParkingLot p WHERE p.id = :id"),
    @NamedQuery(name = "ParkingLot.findByName", query = "SELECT p FROM ParkingLot p WHERE p.name = :name"),
    @NamedQuery(name = "ParkingLot.findByAddress", query = "SELECT p FROM ParkingLot p WHERE p.address = :address"),
    @NamedQuery(name = "ParkingLot.findByTotalSlots", query = "SELECT p FROM ParkingLot p WHERE p.totalSlots = :totalSlots"),
    @NamedQuery(name = "ParkingLot.findByPricePerHour", query = "SELECT p FROM ParkingLot p WHERE p.pricePerHour = :pricePerHour"),
    @NamedQuery(name = "ParkingLot.findByIsActive", query = "SELECT p FROM ParkingLot p WHERE p.isActive = :isActive"),
    @NamedQuery(name = "ParkingLot.findByCreatedAt", query = "SELECT p FROM ParkingLot p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "ParkingLot.findByUpdatedAt", query = "SELECT p FROM ParkingLot p WHERE p.updatedAt = :updatedAt")})
public class ParkingLot implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_slots")
    private int totalSlots;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price_per_hour")
    private float pricePerHour;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinTable(name = "parkinglot_extension", joinColumns = {
        @JoinColumn(name = "parkinglot_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "extension_id", referencedColumnName = "id")})
    @ManyToMany
    private Set<Extension> extensionSet;
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "parkingLotId")
    private Set<ParkingSpace> parkingSpaceSet;

    public ParkingLot() {
    }

    public ParkingLot(Integer id) {
        this.id = id;
    }

    public ParkingLot(Integer id, String name, String address, int totalSlots, float pricePerHour) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.totalSlots = totalSlots;
        this.pricePerHour = pricePerHour;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public float getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Extension> getExtensionSet() {
        return extensionSet;
    }

    public void setExtensionSet(Set<Extension> extensionSet) {
        this.extensionSet = extensionSet;
    }

    public Set<ParkingSpace> getParkingSpaceSet() {
        return parkingSpaceSet;
    }

    public void setParkingSpaceSet(Set<ParkingSpace> parkingSpaceSet) {
        this.parkingSpaceSet = parkingSpaceSet;
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
        if (!(object instanceof ParkingLot)) {
            return false;
        }
        ParkingLot other = (ParkingLot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParkingLot{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", address='" + address + '\''
                + ", totalSlots=" + totalSlots
                + ", extensionSet=" + extensionSet
                + '}';
    }
    
}
