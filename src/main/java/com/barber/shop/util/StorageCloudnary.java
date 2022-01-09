package com.barber.shop.util;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.ClienteSistema;
import com.barber.shop.model.SubFolders;
import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StorageCloudnary {

    @Value("${key.project}")
    private String rootFolder;

    public StorageCloudnary() {
        Cloudinary cloudinary = new Cloudinary(configOpenCloudinary());
        Singleton.registerCloudinary(cloudinary);
    }

    private Map configOpenCloudinary() {
        Map chave = ObjectUtils.asMap(
            "cloud_name", "sud",
            "api_key", "414869814418293",
            "api_secret", "mWG1plNyyL8ufVQEiNNF9NnIcZw");
        return chave;
    }
    
    public List<SubFolders> getFoldersCloudnary() {
        try {
            Api api = Singleton.getCloudinary().api();
            Map map = api.subFolders(rootFolder, ObjectUtils.emptyMap());
            List<HashMap<String,Object>> folders = (List<HashMap<String,Object>>) map.get("folders");
            List<SubFolders> folderses = new ArrayList<>();
            SubFolders subFolders = new SubFolders();
            for(HashMap<String,Object> hashMap : folders) {
                for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                    if(entry.getKey().equalsIgnoreCase("path")) {
                        subFolders.setPath(entry.getValue().toString());
                    } else if (entry.getKey().equalsIgnoreCase("name")) {
                        subFolders.setName(entry.getValue().toString());
                        folderses.add(subFolders);
                        subFolders = new SubFolders();
                    }
                }
            }
            return folderses;
        } catch (Exception ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }

    public void uploadFoto(byte[] dataImage,String folder) {
        try {
            Map conf = ObjectUtils.asMap("public_id", folder);
            Singleton.getCloudinary().uploader().upload(dataImage, conf);
        } catch (IOException ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }
    
    public void createFolders(ClienteSistema clienteSistema) {
        try {
            String pastaPrincipalDoCliente = clienteSistema.getPastaPrincipal().concat("/").concat(clienteSistema.getCpfCnpj());
            Singleton.getCloudinary().api().createFolder(pastaPrincipalDoCliente, ObjectUtils.emptyMap());
            createFolderCatalogo(pastaPrincipalDoCliente.concat("/").concat(clienteSistema.getPastaCatalago()));
            createFolderUsuarioSistema(pastaPrincipalDoCliente.concat("/").concat(clienteSistema.getPastaImagensUsuarioSistema()));
        } catch (Exception ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }
    
    public void createFolder(String nome) {
        try {
            Singleton.getCloudinary().api().createFolder(rootFolder.concat("/").concat(nome), ObjectUtils.emptyMap());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
    
    private void createFolderCatalogo(String path) {
        try {
            Singleton.getCloudinary().api().createFolder(path, ObjectUtils.emptyMap());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
    
    private void createFolderUsuarioSistema(String path) {
        try {
            Singleton.getCloudinary().api().createFolder(path, ObjectUtils.emptyMap());
        } catch (Exception ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }
    
    public void deleteFoto(String folder) {
        try {
            if(!StringUtils.isBlank(folder)) {
                Map confCandidato = ObjectUtils.asMap("resource_type","image","invalidate","true");
                Singleton.getCloudinary().uploader().destroy(folder,confCandidato);
            }
        } catch(IOException ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }
}
