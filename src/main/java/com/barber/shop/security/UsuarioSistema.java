package com.barber.shop.security;

import com.barber.shop.model.Usuario;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User {

    private final Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
