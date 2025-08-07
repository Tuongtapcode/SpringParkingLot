/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.ParkingLot;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nnt.repositories.ParkingLotRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.hibernate.Hibernate;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class ParkingLotRepositoryImpl implements ParkingLotRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 5;

    @Override
    public List<ParkingLot> getParkingLots(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ParkingLot> q = b.createQuery(ParkingLot.class);
        Root root = q.from(ParkingLot.class);
        q.select(root);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }
            String address = params.get("address");
            if (address != null && !address.isEmpty()) {
                predicates.add(b.like(root.get("address"), String.format("%%%s%%", address)));
            }
            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("pricePerHour"), fromPrice));
            }

            q.where(predicates.toArray(Predicate[]::new));
            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }

        }

        Query query = s.createQuery(q);
        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);

        }
        return query.getResultList();
    }

    @Override
    public ParkingLot getParkingLotById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingLot p = s.get(ParkingLot.class, id);

        // Ép Hibernate load extensionSet ngay khi còn session
        Hibernate.initialize(p.getExtensionSet());

        return p;
    }

    @Override
    public void addOrUpdateParkingLot(ParkingLot p
    ) {
        Session s = this.factory.getObject().getCurrentSession();
        Date now = new Date();

        if (p.getId() == null) {
            p.setCreatedAt(now);
            p.setUpdatedAt(now);
            s.persist(p);
        } else {
//            ParkingLot existing = s.get(ParkingLot.class, p.getId());
//            p.setCreatedAt(existing.getCreatedAt());
            p.setCreatedAt(this.getParkingLotById(p.getId()).getCreatedAt());
            p.setUpdatedAt(now); // gán thời gian cập nhật
            s.merge(p);
        }
    }

    @Override
    public void deleteParkingLot(int id
    ) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingLot p = this.getParkingLotById(id);
        if (p != null) {
            p.getExtensionSet().clear();
            s.remove(p);
        }

    }

    @Override
    public List<ParkingLot> getParkingLotByIds(List<Integer> ids
    ) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM ParkingLot  WHERE id IN (:ids)", ParkingLot.class).setParameter("ids", ids);
        return q.getResultList();
    }
}
