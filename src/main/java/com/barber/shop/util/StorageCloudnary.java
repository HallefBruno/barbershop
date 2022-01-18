package com.barber.shop.util;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.ClienteSistema;
import com.barber.shop.model.SubFolders;
import com.barber.shop.service.UsuarioService;
import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StorageCloudnary {

    @Value("${key.project}")
    private String rootFolder;
    
    @Autowired
    private UsuarioService usuario;
    
    private final static String PUBLIC_ID = "public_id";
    private final static String RESOURCE_TYPE = "resource_type";
    private final static String IMAGE = "image";
    private final static String INVALIDATE = "invalidate";
    private final static String TRUE = "true";

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
            Map conf = ObjectUtils.asMap(PUBLIC_ID, folder);
            Singleton.getCloudinary().uploader().upload(dataImage, conf);
        } catch (IOException ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }

    public void createFolders(final ClienteSistema clienteSistema) {
        try {
            String pastaPrincipalDoCliente = clienteSistema.getPastaPrincipal().concat("/").concat(clienteSistema.getCpfCnpj());
            String pastaCatalogo = pastaPrincipalDoCliente.concat("/").concat(clienteSistema.getPastaCatalago());
            String pastaImagensUsuarioSistema = pastaPrincipalDoCliente.concat("/").concat(clienteSistema.getPastaImagensUsuarioSistema());
            
            Singleton.getCloudinary().api().createFolder(pastaPrincipalDoCliente, ObjectUtils.emptyMap());
            Singleton.getCloudinary().api().createFolder(pastaCatalogo, ObjectUtils.emptyMap());
            Singleton.getCloudinary().api().createFolder(pastaImagensUsuarioSistema, ObjectUtils.emptyMap());
            
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
    
    public void uploadFotoCatalogo(byte[] dataImage,String nomeFoto) {
        try {
            String folder = urlCatalogo().concat(nomeFoto);
            Map conf = ObjectUtils.asMap(PUBLIC_ID, folder);
            Singleton.getCloudinary().uploader().upload(dataImage, conf);
        } catch (IOException ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }
    
    public void deleteFotoCatalogo(String nomeFoto) {
        try {
            if(!StringUtils.isBlank(nomeFoto)) {
                String folder = urlCatalogo().concat(nomeFoto);
                Map confCandidato = ObjectUtils.asMap(RESOURCE_TYPE,IMAGE,INVALIDATE,TRUE);
                Singleton.getCloudinary().uploader().destroy(folder,confCandidato);
            }
        } catch(IOException ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }

    public void deleteFoto(String nome) {
        try {
            if(!StringUtils.isBlank(nome)) {
                Map confCandidato = ObjectUtils.asMap(RESOURCE_TYPE,IMAGE,INVALIDATE,TRUE);
                Singleton.getCloudinary().uploader().destroy(rootFolder.concat("/").concat(nome),confCandidato);
            }
        } catch(IOException ex) {
            throw new NegocioException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        }
    }
    
    private String urlCatalogo() {
        ClienteSistema clienteSistema = usuario.getUsuarioLogado().getClienteSistema();
        String pastaPrincipal = clienteSistema.getPastaPrincipal();
        String pastaCatalogo = clienteSistema.getPastaCatalago();
        String cpfCnpj = clienteSistema.getCpfCnpj();
        String folder = pastaPrincipal.concat("/").concat(cpfCnpj).concat("/").concat(pastaCatalogo).concat("/");
        return folder;
    }
}
