/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import com.nnt.pojo.Extension;
import com.nnt.pojo.ParkingLot;
import java.util.List;

/**
 *
 * @author ngoct
 */
public interface ExtensionService {

    List<Extension> getExtensions();

    List<Extension> getExtensionByIds(List<Integer> ids);

    Extension getExtensionById(int id);

    void addOrUpdateExtension(Extension ex);

    void deleteExtension(int id);
}
