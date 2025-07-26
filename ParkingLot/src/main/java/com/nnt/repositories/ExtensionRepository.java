/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.repositories;

import com.nnt.pojo.Extension;
import java.util.List;

/**
 *
 * @author ngoct
 */
public interface ExtensionRepository {

    public List<Extension> getExtensions();

    List<Extension> getExtensionByIds(List<Integer> ids);

    Extension getExtensionById(int id);

    void addOrUpdateExtension(Extension ex);
    
    void deleteExtension(int id);
}
