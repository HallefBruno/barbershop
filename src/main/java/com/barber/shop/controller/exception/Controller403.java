package com.barber.shop.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/403")
public class Controller403 {

  @GetMapping
  public String page403() {
    return "403";
  }
}
