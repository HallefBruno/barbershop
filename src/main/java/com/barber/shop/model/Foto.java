
package com.barber.shop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
    
    @Column(nullable = false, name = "nome_foto", unique = true, length = 100)
    private String nomeFoto;
    
    @Column(nullable = false, name = "original_name", length = 200)
    private String originalName;
    
    @Column(nullable = false, length = 20, name = "cpf_cnpj")
    private String cpfCnpj;
    
    @Column(nullable = false, length = 5)
    private String extensao;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.cpfCnpj = StringUtils.getDigits(this.cpfCnpj);
    }
    
}
