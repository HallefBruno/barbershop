package com.barber.shop.controller;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.Foto;
import com.barber.shop.service.ImportarFotosService;
import com.barber.shop.service.UsuarioService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/importar-fotos")
@RequiredArgsConstructor
public class ImportarFotosController {

  private final UsuarioService usuarioService;
  private final ImportarFotosService importarFotosService;

  @GetMapping
  public ModelAndView pageNovo(Foto foto) {
    ModelAndView mv = new ModelAndView("catalogo/ImportarFotos");
    mv.addObject("clienteSistema", usuarioService.getUsuarioLogado().getClienteSistema());
    mv.addObject("listaFotos", importarFotosService.findAllByCpfCnpj());
    return mv;
  }

  @PostMapping
  public ModelAndView salvar(@Valid Foto foto, @RequestParam("image") MultipartFile multipartFile, BindingResult result, RedirectAttributes attributes) {
    try {
      importarFotosService.salvar(multipartFile);
    } catch (NegocioException ex) {
      result.rejectValue("nomeFoto", ex.getReason(), ex.getMessage());
      return pageNovo(foto);
    }
    attributes.addFlashAttribute("mensagem", "Foto salva com sucesso!");
    return new ModelAndView("redirect:/importar-fotos", HttpStatus.CREATED);
  }

  @ResponseBody
  @DeleteMapping("/{codigo}")
  public ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
    try {
      importarFotosService.excluir(codigo);
    } catch (NegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.noContent().build();
  }
}
