package com.barber.shop.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@EqualsAndHashCode
public class ValidarCliente implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, unique = true, nullable = false)
  private Long id;

  @Column(name = "data_validacao", nullable = false)
  private LocalDateTime dataValidacao;

  @Column(name = "cpf_cnpj", nullable = false, unique = true)
  private String cpfCnpj;

  @Column(name = "conta_criada", columnDefinition = "boolean default false")
  private Boolean contaCriada;

  @PrePersist
  @PreUpdate
  private void prePersistPreUpdate() {
    this.cpfCnpj = StringUtils.getDigits(this.cpfCnpj);
    if (Objects.isNull(this.contaCriada)) {
      this.contaCriada = Boolean.FALSE;
    }
  }

}
