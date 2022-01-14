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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@EqualsAndHashCode
@DynamicUpdate
@Table(name = "cliente_sistema")
public class ClienteSistema implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;
    
    @NotBlank(message = "Nome do comercio não pode ter espaços em branco!")
    @NotEmpty(message = "Nome do comercio não pode ser vazio!")
    @NotNull(message = "Nome do comercio não pode ser null!")
    @Column(name = "nome_comercio", nullable = false)
    private String nomeComercio;
    
    @NotBlank(message = "Telefone não pode ter espaços em branco!")
    @NotEmpty(message = "Telefone não pode ser vazio!")
    @NotNull(message = "Telefone não pode ser null!")
    @Column(name = "telefone", nullable = false, unique = true, length = 15)
    private String telefone;
    
    @NotBlank(message = "CPF/CNPJ não pode ter espaços em branco!")
    @NotEmpty(message = "CPF/CNPJ não pode ser vazio!")
    @NotNull(message = "CPF/CNPJ não pode ser null!")
    @Column(name = "cpf_cnpj", nullable = false, unique = true)
    private String cpfCnpj;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    
    @NotBlank(message = "Estado não pode ter espaços em branco!")
    @NotEmpty(message = "Estado não pode ser vazio!")
    @NotNull(message = "Estado não pode ser null!")
    @Column(name = "estado", nullable = false)
    private String estado;
    
    @NotBlank(message = "Cidade não pode ter espaços em branco!")
    @NotEmpty(message = "Cidade não pode ser vazio!")
    @NotNull(message = "Cidade não pode ser null!")
    @Column(name = "cidade", nullable = false)
    private String cidade;
    
    @NotBlank(message = "Bairro não pode ter espaços em branco!")
    @NotEmpty(message = "Bairro não pode ser vazio!")
    @NotNull(message = "Bairro não pode ser null!")
    @Column(name = "bairro", nullable = false)
    private String bairro;
    
    @NotBlank(message = "CEP não pode ter espaços em branco!")
    @NotEmpty(message = "CEP não pode ser vazio!")
    @NotNull(message = "CEP não pode ser null!")
    @Column(nullable = false)
    private String cep;
    
    @NotBlank(message = "Logradouro não pode ter espaços em branco!")
    @NotEmpty(message = "Logradouro não pode ser vazio!")
    @NotNull(message = "Logradouro não pode ser null!")
    @Column(nullable = false)
    private String logradouro;
    
    @NotBlank(message = "Pasta principal não pode ter espaços em branco!")
    @NotEmpty(message = "Pasta principal não pode ser vazio!")
    @NotNull(message = "Pasta principal não pode ser null!")
    @Column(nullable = false, length = 150)
    private String pastaPrincipal;
    
    @NotBlank(message = "Pasta catálogo não pode ter espaços em branco!")
    @NotEmpty(message = "Pasta catálogo não pode ser vazio!")
    @NotNull(message = "Pasta catálogo não pode ser null!")
    @Column(nullable = false, length = 150)
    private String pastaCatalago;
    
    @NotBlank(message = "Pasta imagens usuario sistema não pode ter espaços em branco!")
    @NotEmpty(message = "Pasta imagens usuario sistema não pode ser vazio!")
    @NotNull(message = "Pasta imagens usuario sistema não pode ser null!")
    @Column(nullable = false, length = 150)
    private String pastaImagensUsuarioSistema;
    
    @NotBlank(message = "Imagem do perfil catalogo não pode ter espaços em branco!")
    @NotEmpty(message = "Imagem do perfil catalogo não pode ser vazio!")
    @NotNull(message = "Imagem do perfil catalogo não pode ser null!")
    @Column(nullable = false, length = 150)
    private String imagePerfilCatalogo;
    
    @NotBlank(message = "Imagem perfil usuario não pode ter espaços em branco!")
    @NotEmpty(message = "Imagem perfil usuario não pode ser vazio!")
    @NotNull(message = "Imagem perfil usuario não pode ser null!")
    @Column(nullable = false, length = 150)
    private String imagemPerfilCadastroUsuario;
    
    @NotBlank(message = "Negocio não pode ter espaços em branco!")
    @NotEmpty(message = "Negocio não pode ser vazio!")
    @NotNull(message = "Negocio não pode ser null!")
    @Column(nullable = false, length = 150)
    private String negocio;
    
    @NotNull(message = "Quantidade usuário não pode ser null!")
    @Column(name = "qtd_usuario", nullable = false)
    private Integer qtdUsuario;
    
    @Column(nullable = false)
    private Boolean ativo;
    
    @Column(name = "acessar_tela_criar_login", columnDefinition = "boolean default false")
    private Boolean acessarTelaCriarLogin;
    
    @Column(name = "primeiro_acesso", columnDefinition = "boolean default false")
    private Boolean primeiroAcesso;
    
    @PrePersist
    private void prePersist() {
        this.dataCadastro = LocalDateTime.now();
        if(Objects.isNull(this.dataAtualizacao))this.dataAtualizacao = this.dataCadastro;
        prepareAtributtes();
    }
    
    @PreUpdate
    private void preUpdate() {
        prepareAtributtes();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    private void prepareAtributtes() {
        this.bairro = StringUtils.strip(this.bairro);
        this.bairro = this.bairro.toLowerCase();
        this.cidade = StringUtils.strip(this.cidade);
        this.cidade = this.cidade.toLowerCase();
        this.estado = StringUtils.strip(this.estado);
        this.pastaPrincipal = StringUtils.strip(this.pastaPrincipal);
        this.pastaCatalago = StringUtils.strip(this.pastaCatalago);
        this.imagePerfilCatalogo = StringUtils.strip(this.imagePerfilCatalogo);
        this.imagemPerfilCadastroUsuario = StringUtils.strip(this.imagemPerfilCadastroUsuario);
        this.pastaImagensUsuarioSistema = StringUtils.strip(this.pastaImagensUsuarioSistema);
        this.negocio = StringUtils.strip(this.negocio);
        this.nomeComercio = StringUtils.strip(this.nomeComercio);
        this.logradouro = StringUtils.strip(this.logradouro);
        this.cep = StringUtils.getDigits(this.cep);
        this.cpfCnpj = StringUtils.getDigits(this.cpfCnpj);
        this.telefone = StringUtils.getDigits(this.telefone);
        
        if(Objects.isNull(this.ativo)) {
            this.ativo = Boolean.FALSE;
        }
        if(Objects.isNull(this.acessarTelaCriarLogin)) {
            this.acessarTelaCriarLogin = Boolean.FALSE;
        }
        if(Objects.isNull(this.primeiroAcesso)) {
            this.primeiroAcesso = Boolean.FALSE;
        }
    }
}

