
package com.barber.shop.service;

import com.barber.shop.model.Foto;
import com.barber.shop.repository.ImportarFotosRepository;
import com.barber.shop.util.StorageCloudnary;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ImportarFotosService {
    
    private final ImportarFotosRepository importarFotosRepository;
    private final UsuarioService usuarioService;
    private final StorageCloudnary storageCloudnary;
    
    @Transactional
    public void salvar(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        Foto foto = new Foto();
        foto.setNomeFoto(fileName.substring(0, fileName.lastIndexOf(".")));
        foto.setExtensao(extension);
        foto.setCpfCnpj(usuarioService.getUsuarioLogado().getClienteSistema().getCpfCnpj());
        try {
            importarFotosRepository.save(foto);
            storageCloudnary.uploadFoto(multipartFile.getBytes(), "fotos-cortes-cabelo/"+foto.getCpfCnpj()+"/"+foto.getNomeFoto());
        } catch (IOException ex) {
            storageCloudnary.deleteFoto("fotos-cortes-cabelo/"+foto.getCpfCnpj()+"/"+foto.getNomeFoto());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        }
    }
    
    public List<Foto> findAllByCpfCnpj() {
        return importarFotosRepository.findAllByCpfCnpj(usuarioService.getUsuarioLogado().getClienteSistema().getCpfCnpj());
    }
    
}
