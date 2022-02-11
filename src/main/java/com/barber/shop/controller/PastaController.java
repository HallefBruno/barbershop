package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.Pasta;
import com.barber.shop.service.PastaService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pasta")
@RequiredArgsConstructor
public class PastaController {

  private final PastaService pastaService;

  @GetMapping
  public ModelAndView page(Pasta pasta) {
    ModelAndView modelAndView = new ModelAndView("pasta/Nova");
    modelAndView.addObject("listaPasta", pastaService.todas());
    modelAndView.addObject("listaPastaNuvem", pastaService.getFoldersNuvem());
    return modelAndView;
  }

  @PostMapping
  public ModelAndView salvar(@Valid Pasta pasta, BindingResult result, RedirectAttributes attributes) {
    try {
      if (result.hasErrors()) {
        return page(pasta);
      }
      pastaService.salvar(pasta);
    } catch (NegocioException ex) {
      result.rejectValue("nome", ex.getMessage(), ex.getReason());
      return page(pasta);
    }
    attributes.addFlashAttribute("mensagem", "Nova pasta cadastrada!");
    return new ModelAndView("redirect:/pasta", HttpStatus.CREATED);

  }

}
