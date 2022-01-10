
package com.barber.shop.repository;

import com.barber.shop.model.Foto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportarFotosRepository extends JpaRepository<Foto, Long> {
    List<Foto> findAllByCpfCnpj(String cpfCnpj);
    Optional<Void> deleteByNomeFotoIgnoreCase(String nome);
    Optional<Foto> findByNomeFotoIgnoreCase(String nome);
}
