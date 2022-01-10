
package com.barber.shop.repository;

import com.barber.shop.model.ImagensSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagensSistemaRepository extends JpaRepository<ImagensSistema, Long> {
    
}
