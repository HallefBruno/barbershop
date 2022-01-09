package com.barber.shop.repository;

import com.barber.shop.model.ClienteSistema;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteSistemaRepository extends JpaRepository<ClienteSistema, Long> {
    Optional<ClienteSistema> findByCpfCnpj(String cnpj);
}
