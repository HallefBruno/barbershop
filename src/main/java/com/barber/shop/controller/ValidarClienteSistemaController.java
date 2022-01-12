package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.service.NovaContaClienteSistema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ValidarClienteSistemaController {

    private final NovaContaClienteSistema validarClienteService;

    @RequestMapping(path = {"validar"}, method = RequestMethod.GET)
    public String pageValidarCliente() {
        return "novaconta/ValidarClienteSistema";
    }

    @GetMapping("validar/cliente")
    public @ResponseBody ResponseEntity<?> clienteCadastrado(@RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            validarClienteService.salvaValidarCliente(cpfCnpj);
            return ResponseEntity.ok().body("novaConta");
        } catch (NegocioException ex) {
            return ResponseEntity.badRequest().body(ex.getReason());
        }
    }

    @RequestMapping(path = {"novaConta"}, method = RequestMethod.GET)
    public String pageNovaConta() {
        return "novaconta/CriarContaClienteSistema";
    }
}
