package com.barber.shop.repository.querys.usuario;

import com.barber.shop.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryCustom {
    public Optional<Usuario> porEmailEAtivo(String email);
    public List<String> permissoes(Usuario usuario);
}
