package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.dto.UsuarioDTO;
import com.barber.shop.service.NovaContaClienteSistema;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String pageNovaConta(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO) {
        return "novaconta/CriarContaClienteSistema";
    }
    
    @RequestMapping(path = {"criar/nova-conta"}, method = RequestMethod.POST)
    public ModelAndView salvar(@Valid UsuarioDTO usuarioDTO, BindingResult result,RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                //return page(pasta);
            }
            //pastaService.salvar(pasta);
        } catch (NegocioException ex) {
            result.rejectValue("nome", ex.getMessage(), ex.getReason());
            //return page(pasta);
        }
        attributes.addFlashAttribute("mensagem", "Conta criada com sucesso!");
        return new ModelAndView("redirect:/", HttpStatus.CREATED);
    }
}
