
package com.barber.shop.service;

import com.barber.shop.exception.NegocioException;
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
        String cpfCnpj = usuarioService.getUsuarioLogado().getClienteSistema().getCpfCnpj();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        Foto foto = new Foto();
        foto.setCpfCnpj(cpfCnpj);
        foto.setNomeFoto(fileName.substring(0, fileName.lastIndexOf(".")));
        foto.setExtensao(extension);
        try {
            importarFotosRepository.findByNomeFotoIgnoreCase(foto.getNomeFoto()).ifPresent((t) -> {
                throw new NegocioException(HttpStatus.BAD_REQUEST, "Essa foto já consta na base de dados!");
            });
            importarFotosRepository.save(foto);
            storageCloudnary.uploadFotoCatalogo(multipartFile.getBytes(), foto.getNomeFoto());
        } catch (IOException ex) {
            storageCloudnary.deleteFotoCatalogo(foto.getNomeFoto());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        }
    }
    
    @Transactional
    public void excluir(Long id) {
        try {
            importarFotosRepository.findById(id)
            .map(foto -> {
                importarFotosRepository.deleteByNomeFotoIgnoreCase(foto.getNomeFoto());
                storageCloudnary.deleteFotoCatalogo(foto.getNomeFoto());
                return Void.TYPE;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (NegocioException ex) {
            throw new NegocioException(HttpStatus.BAD_REQUEST, ex.getReason());
        }
    }
    
    public List<Foto> findAllByCpfCnpj() {
        return importarFotosRepository.findAllByCpfCnpj(usuarioService.getUsuarioLogado().getClienteSistema().getCpfCnpj());
    }
    
}
