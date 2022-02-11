package com.barber.shop.repository.querys.usuario;

import com.barber.shop.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<String> permissoes(Usuario usuario) {
    return manager.createQuery("select distinct p.nome from Usuario u join u.grupos g join g.permissoes p where u = :usuario", String.class)
      .setParameter("usuario", usuario)
      .getResultList();
  }

}
