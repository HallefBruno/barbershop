package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.LoginCliente;
import com.barber.shop.repository.LoginClienteRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = {"/login-cliente"})
@RequiredArgsConstructor
public class ClienteLoginController {

  private final LoginClienteRepository clienteRepository;

  @GetMapping
  public ModelAndView pageLoginCliente(LoginCliente loginCliente) {
    return new ModelAndView("LoginCliente");
  }

  @PostMapping
  public ModelAndView validarLoginUsuario(@Valid LoginCliente loginCliente, Model model, BindingResult result, RedirectAttributes attributes) {
    try {
      if (result.hasErrors()) {
        return pageLoginCliente(loginCliente);
      }
      if (clienteRepository.findByEmailIgnoreCaseAndTelefone(loginCliente.getEmail(), StringUtils.getDigits(loginCliente.getTelefone())).isPresent()) {
        return new ModelAndView("redirect:/novo-agendamento", HttpStatus.OK);
      }
    } catch (NegocioException ex) {
      ObjectError error = new ObjectError("erro", ex.getMessage());
      result.addError(error);
      return pageLoginCliente(loginCliente);
    }
    attributes.addFlashAttribute("mensagem", "Conta inexistente!");
    return new ModelAndView("redirect:/login-cliente", HttpStatus.BAD_REQUEST);
  }
}
