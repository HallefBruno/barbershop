package com.barber.shop.repository;

import com.barber.shop.model.Pasta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long> {

  Optional<Pasta> findByNomeIgnoreCase(String nome);
}
