/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nnt.pojo.Extension;
import com.nnt.pojo.ParkingLot;
import com.nnt.repositories.ExtensionRepository;
import com.nnt.services.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnt.repositories.ParkingLotRepository;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngoct
 */
@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    
    @Autowired
    private ExtensionRepository extensionRepository;
    
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public List<ParkingLot> getParkingLots(Map<String, String> params) {
        return this.parkingLotRepository.getParkingLots(params);
    }
    
    @Override
    public ParkingLot getParkingLotById(int id) {
        return this.parkingLotRepository.getParkingLotById(id);
    }
    
    @Override
    public void addOrUpdateParkingLot(ParkingLot p, List<Integer> extensionIds) {
        
        Set<Extension> extensions = new HashSet<>();
        
        if (extensionIds != null && !extensionIds.isEmpty()) {
            List<Extension> extList = this.extensionRepository.getExtensionByIds(extensionIds);
            extensions.addAll(extList);
        }
        
        p.setExtensionSet(extensions); // Gán extensionSet

        if (!p.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(p.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                p.setImage(res.get("secure_url").toString());
            } catch (IOException exception) {
                Logger.getLogger(ParkingLotService.class.getName()).log(Level.SEVERE, null, exception);
            }
        } else {
            if (p.getId() != null) {
                p.setImage(this.extensionRepository.getExtensionById(p.getId()).getImage());
            } else {
                p.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1ZJRoCpdYp01FkOx2Ej08IMk72EOajMkTw&s");
                
            }
        }
        this.parkingLotRepository.addOrUpdateParkingLot(p);
        
    }
    
    @Override
    public void deleteParkingLot(int id) {
        this.parkingLotRepository.deleteParkingLot(id);
    }
}
