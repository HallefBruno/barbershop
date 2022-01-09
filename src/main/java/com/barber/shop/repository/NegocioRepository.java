
package com.barber.shop.repository;

import com.barber.shop.model.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    
}
