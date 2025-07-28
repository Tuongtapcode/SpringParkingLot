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
import com.nnt.repositories.ParkingLotRepository;
import com.nnt.services.ExtensionService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngoct
 */
@Service

public class ExtensionServiceImpl implements ExtensionService {

    @Autowired
    private ExtensionRepository extensionRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Extension> getExtensions() {
        return this.extensionRepository.getExtensions();
    }

    @Override
    public List<Extension> getExtensionByIds(List<Integer> ids) {
        return this.extensionRepository.getExtensionByIds(ids);
    }

    @Override
    public Extension getExtensionById(int id) {
        return this.extensionRepository.getExtensionById(id);
    }

    @Override
    public void addOrUpdateExtension(Extension ex) {
        if (!ex.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(ex.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                ex.setImage(res.get("secure_url").toString());
            } catch (IOException exception) {
                Logger.getLogger(ExtensionService.class.getName()).log(Level.SEVERE, null, exception);
            }
        } else {
            if (ex.getId() != null) {
                ex.setImage(this.extensionRepository.getExtensionById(ex.getId()).getImage());
            } else {
                ex.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1ZJRoCpdYp01FkOx2Ej08IMk72EOajMkTw&s");

            }
        }
        this.extensionRepository.addOrUpdateExtension(ex);
    }

    @Override
    public void deleteExtension(int id) {
        this.extensionRepository.deleteExtension(id);
    }

}
