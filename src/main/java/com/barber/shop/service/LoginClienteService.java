package com.barber.shop.service;

import com.barber.shop.model.LoginCliente;
import com.barber.shop.repository.LoginClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginClienteService {
    
    private final LoginClienteRepository clienteRepository;
    
    
    public void usuarioExistente(LoginCliente loginCliente) {
        clienteRepository.findByEmailIgnoreCaseAndTelefone(loginCliente.getEmail(), loginCliente.getTelefone())
        .map(usuario -> {
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
}
