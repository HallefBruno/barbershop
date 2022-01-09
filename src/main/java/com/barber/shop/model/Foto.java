
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
@SuppressWarnings("PersistenceUnitPresent")
public class Foto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;
    
    @NotBlank(message = "CPF/CNPJ não pode ter espaços em branco!")
    @NotEmpty(message = "CPF/CNPJ não pode ser vazio!")
    @NotNull(message = "CPF/CNPJ não pode ser null!")
    @Column(name = "cpf_cnpj", nullable = false)
    private String cpfCnpj;
    
    @Column(nullable = false, name = "nome_foto", unique = true)
    private String nomeFoto;
    
    @Column(nullable = false)
    private String extensao;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.cpfCnpj = StringUtils.getDigits(this.cpfCnpj);
    }
}
