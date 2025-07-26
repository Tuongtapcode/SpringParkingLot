/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.Extension;
import com.nnt.pojo.ParkingLot;

import com.nnt.repositories.ExtensionRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class ExtensionRepositoryImpl implements ExtensionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Extension> getExtensions() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Extension", Extension.class);
        return q.getResultList();
    }

    @Override
    public List<Extension> getExtensionByIds(List<Integer> ids) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Extension  WHERE id IN (:ids)", Extension.class).setParameter("ids", ids);
        return q.getResultList();
    }

    @Override
    public Extension getExtensionById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Extension e = s.get(Extension.class, id);
        Hibernate.initialize(e.getParkingLotSet());
        return e;
    }

    @Override
    public void addOrUpdateExtension(Extension ex) {
        Session s = this.factory.getObject().getCurrentSession();
        if (ex.getId() == null) {
            s.persist(ex);
        } else {
            s.merge(ex);
        }
    }

    @Override
    public void deleteExtension(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Extension e = this.getExtensionById(id);
        if (e != null) {
            // Xóa liên kết ở phía ParkingLot (owning side)
            for (ParkingLot pl : e.getParkingLotSet()) {
                pl.getExtensionSet().remove(e);
            }

            // Xóa ngược lại (giữ đồng bộ trong bộ nhớ)
            e.getParkingLotSet().clear();

            s.remove(e); // Hibernate sẽ xóa khỏi bảng extension và bảng trung gian
        }
    }
}
