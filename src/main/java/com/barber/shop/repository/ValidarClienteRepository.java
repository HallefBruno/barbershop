
package com.barber.shop.repository;

import com.barber.shop.model.ValidarCliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidarClienteRepository extends JpaRepository<ValidarCliente, Long>{
    Optional<ValidarCliente> findByCpfCnpj(String cpfCnpj);
}
