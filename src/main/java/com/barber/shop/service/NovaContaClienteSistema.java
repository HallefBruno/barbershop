
package com.barber.shop.service;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.ClienteSistema;
import com.barber.shop.model.ValidarCliente;
import com.barber.shop.repository.ClienteSistemaRepository;
import com.barber.shop.repository.ValidarClienteRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NovaContaClienteSistema {

    private final ValidarClienteRepository clienteRepository;
    private final ClienteSistemaRepository clienteSistemaRepository;

    @Transactional
    public void salvaValidarCliente(String cpfCnpj) {
        Optional<ValidarCliente> temCnpj = clienteRepository.findByCpfCnpj(cpfCnpj);
        if (temCnpj.isPresent() && temCnpj.get().getContaCriada()) {
            throw new NegocioException(HttpStatus.BAD_REQUEST,"Esse cliente já possui conta!");
        } else if (!temCnpj.isPresent()) {
            verificaSeClienteEstaCadastrado(cpfCnpj);
        }
    }

    private void verificaSeClienteEstaCadastrado(String cpfCnpj) {
        Optional<ClienteSistema> opClienteSistema = clienteSistemaRepository.findByCpfCnpj(cpfCnpj);
        if (opClienteSistema.isPresent()) {
            ClienteSistema clienteSistema = opClienteSistema.get();
            if (clienteSistema.getPrimeiroAcesso() && clienteSistema.getAcessarTelaCriarLogin()) {
                ValidarCliente validarCliente = new ValidarCliente();
                validarCliente.setCpfCnpj(cpfCnpj);
                validarCliente.setDataValidacao(LocalDateTime.now());
                clienteRepository.save(validarCliente);
            }
        } else {
            throw new NegocioException(HttpStatus.BAD_REQUEST,"Cliente sem permisão para criar conta!");
        }
    }
}
