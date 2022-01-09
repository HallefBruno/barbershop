package com.barber.shop.service;

import com.barber.shop.model.Grupo;
import com.barber.shop.repository.GrupoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrupoService {
    
    private final GrupoRepository grupoRepository;
    
    public List<Grupo> gupos() {
        return grupoRepository.findAll();
    }
    
}
