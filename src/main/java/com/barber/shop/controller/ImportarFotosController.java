package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.service.ImportarFotosService;
import com.barber.shop.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/importar-fotos")
@RequiredArgsConstructor
public class ImportarFotosController {

    @Value("${key.project}")
    private String keyProject;
    private final UsuarioService usuarioService;
    private final ImportarFotosService importarFotosService;

    @GetMapping
    public ModelAndView pageNovo(Model model) {
        model.addAttribute("clienteSistema", usuarioService.getUsuarioLogado().getClienteSistema());
        model.addAttribute("listaFotos", importarFotosService.findAllByCpfCnpj());
        model.addAttribute("keyProject", keyProject);
        return new ModelAndView("catalogo/ImportarFotos");
    }

    @PostMapping
    public ModelAndView salvar(@RequestParam("image") MultipartFile multipartFile, Model model, RedirectAttributes attributes) {
        try {
            importarFotosService.salvar(multipartFile);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            attributes.addFlashAttribute(error);
            return pageNovo(model);
        }
        attributes.addFlashAttribute("mensagem", "Foto salva com sucesso!");
        return new ModelAndView("redirect:/importar-fotos", HttpStatus.CREATED);
    }
}
