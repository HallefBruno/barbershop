
package com.barber.shop.repository;

import com.barber.shop.model.Foto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportarFotosRepository extends JpaRepository<Foto, Long> {
    List<Foto> findAllByCpfCnpj(String cpfCnpj);
}
