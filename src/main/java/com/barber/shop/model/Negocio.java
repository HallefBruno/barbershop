package com.barber.shop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Entity
public class Negocio implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, unique = true, nullable = false)
  private Long id;

  @NotBlank(message = "Nome não pode ter espaços em branco!")
  @NotEmpty(message = "Nome não pode ser vazio!")
  @NotNull(message = "Nome não pode ser null!")
  @Column(name = "nome", nullable = false, unique = true, length = 150)
  private String nome;

  @PrePersist
  @PreUpdate
  private void prePersistPreUpdate() {
    this.nome = StringUtils.strip(this.nome);
  }

}
