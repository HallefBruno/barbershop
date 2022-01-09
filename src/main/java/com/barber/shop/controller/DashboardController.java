package com.barber.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @GetMapping
    public String pagePrincipal() {
        return "dashboard/Dashboard";
    }
    
}
