package com.barber.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NovaContaCriadaController {

  @GetMapping("/conta-criada")
  public ModelAndView page(@ModelAttribute("resultado") String resultado) {
    ModelAndView modelAndView = new ModelAndView("NovaContaCriada");
    modelAndView.addObject("resultado", resultado);
    return modelAndView;
  }

}
