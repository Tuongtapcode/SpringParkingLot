/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.Payment;
import com.nnt.pojo.PaymentRequest;
import com.nnt.pojo.Reservation;
import com.nnt.pojo.User;
import com.nnt.repositories.PaymentRepository;
import com.nnt.repositories.UserRepository;
import com.nnt.services.ReservationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class PaymentRepositoryImpl implements PaymentRepository {

    private static final int PAGE_SIZE = 6;
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationService reservationService;

    @Override
    public Payment pays(List<PaymentRequest> paymentRequest) {
        Session s = this.factory.getObject().getCurrentSession();
        Payment p = new Payment();
        p.setUserId(this.userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));// update current user
        Set<Reservation> reservationSet = new HashSet<>();
        p.setMethod("VNPAY");
        // Lấy method từ phần tử đầu tiên
        if (!paymentRequest.isEmpty()) {
            p.setMethod(paymentRequest.get(0).getMethod());
        }

        for (var pm : paymentRequest) {

            reservationSet.add(this.reservationService.getReservationById(pm.getReservationId()));
        }
        p.setReservationSet(reservationSet);

        double totalAmount = 0.0;
        for (Reservation r : reservationSet) {
            r.setPaymentId(p);
            r.setStatus("CONFIRM");
            double baseAmount = r.getBaseAmount();
            double extraFee = r.getExtraFee();
            double discount = r.getDiscount();
            double totalAmountTemp = baseAmount + extraFee - discount;
            totalAmount += totalAmountTemp;

        }
        p.setAmount(totalAmount);
        String description = String.format("Thanh toán dịch vụ EasyParking: %.1f VND", totalAmount);
        p.setDescription(description);
        p.setCreatedAt(new Date());
        String transaction = String.format("VNPAY_%s", p.getCreatedAt());
        p.setTransactionId(transaction);
        p.setUpdatedAt(new Date());
        p.setStatus("COMPLETED");
        s.persist(p);
        return p;
    }

    @Override
    public List<Payment> getPayments(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Payment> q = b.createQuery(Payment.class);
        Root root = q.from(Payment.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        //Lấy thông tin user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.getUserByUsername(username);
        predicates.add(b.equal(root.get("userId"), user));
        q.where(predicates.toArray(Predicate[]::new));
        q.orderBy(b.desc(root.get("id")));
        //Phân trang
        Query query = s.createQuery(q);
        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        return query.getResultList();
    }

}
