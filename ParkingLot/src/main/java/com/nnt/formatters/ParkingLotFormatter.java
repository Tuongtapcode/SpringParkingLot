///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.nnt.formatters;
//
//import com.nnt.pojo.ParkingLot;
//import java.text.ParseException;
//import java.util.Locale;
//import org.springframework.format.Formatter;
//
///**
// *
// * @author ngoct
// */
//public class ParkingLotFormatter implements Formatter<ParkingLot> {
//
//    @Override
//    public String print(ParkingLot parkingLot, Locale locale) {
//        return String.valueOf(parkingLot.getId());
//    }
//
//    @Override
//    public ParkingLot parse(String id, Locale locale) throws ParseException {
//        ParkingLot p = new ParkingLot();
//        p.setId(Integer.valueOf(id));
//        return p;
//    }
//}
