package com.barber.shop.repository;

import com.barber.shop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Usuario, Long>{
    
}
