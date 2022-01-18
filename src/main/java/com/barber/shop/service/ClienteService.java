package com.barber.shop.service;

import com.barber.shop.model.Usuario;
import com.barber.shop.repository.ClienteRepository;
import com.barber.shop.util.StorageCloudnary;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final StorageCloudnary storageCloudnary;
    
    @Transactional
    public void salvar(Usuario cliente) {
        clienteRepository.save(cliente);
    }
    
    @Transactional
    public void salvar(Usuario cliente, MultipartFile multipartFile) {
        String nomeArquivo = "";
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        cliente.setNomeFoto(UUID.randomUUID().toString());
        cliente.setExtensao(extension);
        Usuario novo = clienteRepository.save(cliente);
        nomeArquivo =  novo.getId().toString()+"-"+cliente.getNomeFoto();
        //storageCloudnary.uploadFotoCliente(multipartFile.getBytes(),nomeArquivo);
    }
}
