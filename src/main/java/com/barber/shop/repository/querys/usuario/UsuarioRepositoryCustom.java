package com.barber.shop.repository.querys.usuario;

import com.barber.shop.model.Usuario;
import java.util.List;

public interface UsuarioRepositoryCustom {
    public List<String> permissoes(Usuario usuario);
}
