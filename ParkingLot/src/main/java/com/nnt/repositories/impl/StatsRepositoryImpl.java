/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.ParkingLot;
import com.nnt.pojo.ParkingSpace;
import com.nnt.pojo.Payment;
import com.nnt.pojo.Reservation;
import com.nnt.repositories.StatsRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

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
    public long blockedParkingSpace() {
        Session session = factory.getObject().getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(p.id) FROM ParkingSpace p WHERE p.status = :status", Long.class);
        query.setParameter("status", "BLOCKED");
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

    @Override
    public List<Object[]> statsRevenueByParkingLot() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<ParkingLot> root = q.from(ParkingLot.class);
        Join<ParkingLot, ParkingSpace> ps = root.join("parkingSpaceSet", JoinType.LEFT);
        Join<ParkingSpace, Reservation> r = ps.join("reservationSet", JoinType.LEFT);
        Join<Reservation, Payment> p = r.join("paymentId", JoinType.LEFT);
        Expression<Double> revenueExpr = b.sum(
                b.<Double>selectCase()
                        .when(b.equal(p.get("status"), "COMPLETED"), p.get("amount"))
                        .otherwise(0.0)
        );
        q.multiselect(
                root.get("id"),
                root.get("name"),
                revenueExpr
        );

        q.groupBy(root.get("id"), root.get("name"));
        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByTime(String time, int year) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Payment> root = q.from(Payment.class);
        Expression<Integer> timeFunc = b.function(time.toUpperCase(), Integer.class, root.get("createdAt"));

        q.multiselect(
                timeFunc,
                b.sum(root.get("amount"))
        );

        Predicate byYear = b.equal(b.function("YEAR", Integer.class, root.get("createdAt")), year);
        Predicate isCompleted = b.equal(root.get("status"), "COMPLETED");

        q.where(b.and(byYear, isCompleted));
        q.groupBy(timeFunc);
        q.orderBy(b.asc(timeFunc));
        return s.createQuery(q).getResultList();
    }

}
