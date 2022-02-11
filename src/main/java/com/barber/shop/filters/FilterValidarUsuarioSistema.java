package com.barber.shop.filters;

import com.barber.shop.model.Usuario;
import com.barber.shop.security.UsuarioSistema;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FilterValidarUsuarioSistema implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = ((UsuarioSistema) authentication.getPrincipal()).getUsuario();
        if (notAccess(usuario)) {
          HttpServletResponse httpResponse = (HttpServletResponse) response;
          httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Sem permissão para acessar o sistema!");
        }
      }
    }
    chain.doFilter(request, response);
  }

  private Boolean notAccess(Usuario usuario) {
    return BooleanUtils.isFalse(usuario.getClienteSistema().getAcessarTelaCriarLogin()) || BooleanUtils.isFalse(usuario.getClienteSistema().getPrimeiroAcesso()) || BooleanUtils.isFalse(usuario.getClienteSistema().getAtivo());
  }

}
