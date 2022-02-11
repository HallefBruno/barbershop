package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.Usuario;
import com.barber.shop.service.NovaContaClienteSistemaService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ValidarClienteSistemaController {

  private final NovaContaClienteSistemaService validarClienteService;

  @RequestMapping(path = {"validar"}, method = RequestMethod.GET)
  public String pageValidarCliente() {
    return "novaconta/ValidarClienteSistema";
  }

  @GetMapping("validar/cliente")
  public @ResponseBody
  ResponseEntity<?> clienteCadastrado(@RequestParam(name = "cpfCnpj") String cpfCnpj) {
    try {
      validarClienteService.validarClienteSistema(cpfCnpj);
      return ResponseEntity.ok().body("novaConta");
    } catch (NegocioException ex) {
      return ResponseEntity.badRequest().body(ex.getReason());
    }
  }

  @RequestMapping(path = {"novaConta"}, method = RequestMethod.GET)
  public ModelAndView pageNovaConta(@ModelAttribute("usuario") Usuario usuario) {
    return new ModelAndView("novaconta/CriarContaClienteSistema");
  }

  @RequestMapping(path = {"criar/nova-conta"}, method = RequestMethod.POST)
  public ModelAndView salvar(@RequestParam("image") MultipartFile multipartFile, @Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
    try {
      if (result.hasErrors()) {
        return pageNovaConta(usuario);
      }
      validarClienteService.criarPreContaUsuarioClienteSistema(multipartFile, usuario);
    } catch (NegocioException ex) {
      ObjectError error = new ObjectError("erro", ex.getReason());
      result.addError(error);
      return pageNovaConta(usuario);
      //result.rejectValue("nome", ex.getMessage(), ex.getReason());
    }
    return new ModelAndView("redirect:/conta-criada", HttpStatus.CREATED).addObject("resultado", "Conta criada com sucesso!");
  }
}
