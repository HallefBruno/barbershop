package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.Usuario;
import com.barber.shop.service.ClienteService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/novaContaCliente")
@RequiredArgsConstructor
public class NovoClienteController {

  private final PasswordEncoder passwordEncoder;
  private final ClienteService clienteService;

  @GetMapping
  public ModelAndView pageInicial(Usuario cliente) {
    return new ModelAndView("agendamento/NovaConta");
  }

  @PostMapping("salvar")
  public ModelAndView salvar(@RequestParam("image") MultipartFile multipartFile, @Valid Usuario cliente, BindingResult result, Model model, RedirectAttributes attributes) {
    try {
      if (result.hasErrors()) {
        return pageInicial(cliente);
      }
      cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
      clienteService.salvar(cliente, multipartFile);
    } catch (NegocioException ex) {
      ObjectError error = new ObjectError("erro", ex.getMessage());
      result.addError(error);
      return pageInicial(cliente);
    }
    attributes.addFlashAttribute("mensagem", "Conta registrada com sucesso!");
    return new ModelAndView("redirect:/agendamento", HttpStatus.CREATED);
  }

}
