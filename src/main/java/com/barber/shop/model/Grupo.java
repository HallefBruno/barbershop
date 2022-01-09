package com.barber.shop.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_permissao"))
    private Set<Permissao> permissoes;

}