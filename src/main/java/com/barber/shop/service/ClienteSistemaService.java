package com.barber.shop.service;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.ClienteSistema;
import com.barber.shop.repository.ClienteSistemaRepository;
import com.barber.shop.util.StorageCloudnary;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteSistemaService {

  private final ClienteSistemaRepository clienteSistemaRepository;
  private final StorageCloudnary storageCloudnary;

  @Transactional
  public void salvar(ClienteSistema clienteSistema) {
    String cnpj = StringUtils.getDigits(clienteSistema.getCpfCnpj());
    clienteSistemaRepository.findByCpfCnpj(cnpj).ifPresent((t) -> {
      throw new NegocioException(HttpStatus.BAD_REQUEST, "Esse CPF/CNPJ já consta na base de dados!");
    });
    clienteSistemaRepository.save(clienteSistema);
    storageCloudnary.createFolders(clienteSistema);
  }

}
