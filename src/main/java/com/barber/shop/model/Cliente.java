package com.barber.shop.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@EqualsAndHashCode
@DynamicUpdate
public class Cliente implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Nome não pode ter espaços em branco!")
  @NotEmpty(message = "Nome não pode ser vazio!")
  @NotNull(message = "Nome não pode ser null!")
  @Column(nullable = false, length = 100)
  private String nome;

  @NotBlank(message = "Telefone não pode ter espaços em branco!")
  @NotEmpty(message = "Telefone não pode ser vazio!")
  @NotNull(message = "Telefone não pode ser null!")
  @Column(nullable = false, length = 11)
  private String telefone;

  @Email
  @NotBlank(message = "Email não pode ter espaços em branco!")
  @NotEmpty(message = "Email não pode ser vazio!")
  @NotNull(message = "Email não pode ser null!")
  @Column(nullable = false, length = 200, unique = true)
  private String email;

//    @NotBlank(message = "Senha não pode ter espaços em branco!")
//    @NotEmpty(message = "Senha não pode ser vazio!")
//    @NotNull(message = "Senha não pode ser null!")
//    @Column(nullable = false, length = 100)
//    private String senha;
  @NotNull(message = "Data nascimento é obrigatória!")
  @Column(name = "data_nascimento", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
  private LocalDate dataNascimento;

  @Column(nullable = false, name = "nome_foto", unique = true)
  private String nomeFoto;

  @Column(nullable = false)
  private String extensao;

  @PrePersist
  @PreUpdate
  private void prePersistPreUpdate() {
    this.telefone = StringUtils.getDigits(this.telefone);
    this.telefone = StringUtils.strip(this.telefone);
    this.nome = StringUtils.strip(this.nome);
    this.email = StringUtils.strip(this.email);
    //this.senha = StringUtils.strip(this.senha);
  }

}
