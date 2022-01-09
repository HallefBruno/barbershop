
package com.barber.shop.service;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.Pasta;
import com.barber.shop.model.SubFolders;
import com.barber.shop.repository.PastaRepository;
import com.barber.shop.util.StorageCloudnary;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PastaService {
    
    private final PastaRepository pastaRepository;
    private final StorageCloudnary storageCloudnary;
    
    @Transactional
    public void salvar(Pasta pasta) {
        pastaRepository.findByNomeIgnoreCase(pasta.getNome()).ifPresent((t) -> {
            throw new NegocioException(HttpStatus.BAD_REQUEST, "Já existe uma pasta com esse nome!");
        });
        pastaRepository.save(pasta);
        storageCloudnary.createFolder(pasta.getNome());
    }
    
    public List<Pasta> todas() {
        return pastaRepository.findAll();
    }
    
    public List<SubFolders> getFoldersNuvem() {
        return storageCloudnary.getFoldersCloudnary();
    }
    
}
