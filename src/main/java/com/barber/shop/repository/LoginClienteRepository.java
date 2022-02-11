package com.barber.shop.repository;

import com.barber.shop.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginClienteRepository extends JpaRepository<Cliente, Long> {

  Optional<Cliente> findByEmailIgnoreCaseAndTelefone(String email, String telefone);
}
