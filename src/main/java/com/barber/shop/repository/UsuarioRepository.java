package com.barber.shop.repository;

import com.barber.shop.model.ClienteSistema;
import com.barber.shop.model.Usuario;
import com.barber.shop.repository.querys.usuario.UsuarioRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryCustom {
    public Optional<Usuario> findByEmailAndAtivoTrue(String email);
    public Optional<Usuario> findByEmailAndClienteSistema(String email, ClienteSistema clienteSistema);
}
