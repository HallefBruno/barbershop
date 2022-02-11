package com.barber.shop.security;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  public static final Logger LOG = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      LOG.log(Level.WARNING, "User: {0} tentou acessar o URL protegido: {1}", new Object[]{auth.getName(), request.getRequestURI()});
    }
    response.sendRedirect(request.getContextPath() + "/403");
  }
}
