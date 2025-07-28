/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.repositories.StatsRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public long totalParkingLot() {
        Session session = factory.getObject().getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(p.id) FROM ParkingLot p", Long.class);
        return query.getSingleResult();
    }

    @Override
    public long totalParkingSpace() {
        Session session = factory.getObject().getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(p.id) FROM ParkingSpace p", Long.class);
        return query.getSingleResult();
    }

    @Override
    public long availableParkingSpace() {
        Session session = factory.getObject().getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(p.id) FROM ParkingSpace p WHERE p.status = :status", Long.class);
        query.setParameter("status", "AVAILABLE");
        return query.getSingleResult();
    }

    @Override
    public double revenueThisMonth() {
        Session session = factory.getObject().getCurrentSession();
        Query<Double> query = session.createQuery(
                "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p "
                + "WHERE MONTH(p.createdAt) = MONTH(CURRENT_DATE) "
                + "AND YEAR(p.createdAt) = YEAR(CURRENT_DATE) "
                + "AND p.status = :status", Double.class
        );

        query.setParameter("status", "COMPLETED");
        return Math.round(query.getSingleResult() * 100.0) / 100.0;
    }

}
