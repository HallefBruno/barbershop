package com.barber.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NegocioException extends ResponseStatusException {

  public NegocioException(HttpStatus status, String mensagem) {
    super(status, mensagem);
  }

}
