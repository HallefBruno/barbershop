package com.barber.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {

  @RequestMapping("/show")
  public String pageIndex() {
    return "Site";
  }

  @RequestMapping("/")
  public String pageShow() {
    return "Site";
  }

}
