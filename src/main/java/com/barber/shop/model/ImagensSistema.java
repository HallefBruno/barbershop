package com.barber.shop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "imagens_sistema")
@SuppressWarnings("PersistenceUnitPresent")
public class ImagensSistema implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, unique = true, nullable = false)
  private Long id;

  @Column(nullable = false, name = "nome", unique = true, length = 100)
  private String nome;

  @Column(nullable = false, length = 20, name = "extensao")
  private String extensao;

}
