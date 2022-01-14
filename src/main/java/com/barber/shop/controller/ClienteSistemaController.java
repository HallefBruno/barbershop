package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.ClienteSistema;
import com.barber.shop.repository.ImagensSistemaRepository;
import com.barber.shop.service.ClienteSistemaService;
import com.barber.shop.service.NegocioService;
import com.barber.shop.util.StorageCloudnary;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente-sistema")
@RequiredArgsConstructor
public class ClienteSistemaController {

    private final ClienteSistemaService clienteSistemaService;
    private final ImagensSistemaRepository imagensSistemaRepository;
    private final NegocioService negocioService;
    private final StorageCloudnary storageCloudnary;

    @GetMapping("/novo")
    public ModelAndView index(ClienteSistema clienteSistema) {
        ModelAndView mv = new ModelAndView("clientesistema/Novo");
        mv.addObject("clienteSistema", clienteSistema);
        mv.addObject("listNegocios", negocioService.todos());
        mv.addObject("listFolders", storageCloudnary.getFoldersCloudnary());
        mv.addObject("imagensSistema", imagensSistemaRepository.findAll());
        return mv;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid ClienteSistema clienteSistema, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return index(clienteSistema);
            }
            clienteSistemaService.salvar(clienteSistema);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getReason());
            //result.rejectValue("erro", e.getMessage(), e.getMessage());
            result.addError(error);
            return index(clienteSistema);
        }
        attributes.addFlashAttribute("mensagem", "Novo cliente cadastrado!");
        return new ModelAndView("redirect:/cliente-sistema/novo", HttpStatus.CREATED);
    }

}
