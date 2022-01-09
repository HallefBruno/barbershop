package com.barber.shop.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
public class Usuario implements Serializable {
    
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
    
    @NotBlank(message = "Senha não pode ter espaços em branco!")
    @NotEmpty(message = "Senha não pode ser vazio!")
    @NotNull(message = "Senha não pode ser null!")
    @Column(nullable = false, length = 100)
    private String senha;
    
    @Column(nullable = false)
    private Boolean ativo;

    @Size(min = 1, message = "Selecione pelo menos um grupo")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_grupo"))
    private Set<Grupo> grupos;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private ClienteSistema clienteSistema;

    @Column(name = "data_nascimento")
    @DateTimeFormat(pattern="yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;
    
    @Column(nullable = false)
    private Boolean proprietario;
    
    
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
        this.senha = StringUtils.strip(this.senha);
        if(Objects.isNull(this.proprietario)) this.proprietario = false;
    }
    
}
