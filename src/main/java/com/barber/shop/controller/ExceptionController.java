package com.barber.shop.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView  dataIntegrityViolationException(Exception ex, final Model model, HttpServletRequest request) {
        String servPath = request.getServletPath();
        String redirect = servPath.substring(0,servPath.indexOf("/",2));
        String msg = ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage();
        model.addAttribute("errorMessage", msg);
        model.addAttribute("path", redirect);
        return new ModelAndView("Error");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatusException(ResponseStatusException ex,Model model,HttpServletRequest request) {
        String messageError = ex.getReason();
        String servPath = request.getServletPath();
        model.addAttribute("errorMessage", messageError);
        model.addAttribute("path", servPath);
        return new ModelAndView("Error");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView nullPointerException(Model model,HttpServletRequest request) {
        String servPath = request.getServletPath();
        String redirect = servPath.substring(0,servPath.indexOf("/",2));
        model.addAttribute("errorMessage", "Erro grave, por favor entrar em contato com admin do sistema!");
        model.addAttribute("path", redirect);
        return new ModelAndView("Error");
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView badRequestError(Exception ex, final Model model, HttpServletRequest request) {
        String servPath = request.getServletPath();
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("path", servPath);
        return new ModelAndView("Error");
    }
}
