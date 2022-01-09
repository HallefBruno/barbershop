
package com.barber.shop.service;

import com.barber.shop.model.Negocio;
import com.barber.shop.repository.NegocioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NegocioService {
    
    private final NegocioRepository negocioRepository;
    
    @Transactional
    public void salvar(Negocio negocio) {
        negocioRepository.save(negocio);
    }
    
    public List<Negocio> todos() {
        return this.negocioRepository.findAll();
    }
    
}
