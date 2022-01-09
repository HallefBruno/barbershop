package com.barber.shop.service;

import com.barber.shop.model.ClienteSistema;
import com.barber.shop.model.Usuario;
import com.barber.shop.repository.UsuarioRepository;
import com.barber.shop.security.UsuarioSistema;
import com.barber.shop.util.StorageCloudnary;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final StorageCloudnary storageCloudnary;
    private final PasswordEncoder passwordEncoder;

    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = ((UsuarioSistema) authentication.getPrincipal()).getUsuario();
        return usuario;
    }

    @Transactional
    public void salvar(Usuario usuario, MultipartFile multipartFile) {
        String nomeArquivo = "";
        ClienteSistema clienteSistema = getUsuarioLogado().getClienteSistema();
        if(verificarExisteUsuario(usuario, clienteSistema)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Esse usuário ja está cadastrado!");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        usuario.setNomeFoto(fileName.substring(0, fileName.lastIndexOf(".")));
        usuario.setExtensao(extension);
        usuario.setClienteSistema(clienteSistema);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novo = usuarioRepository.save(usuario);
        nomeArquivo = novo.getId().toString() + "-" + usuario.getNomeFoto();
        //storageCloudnary.uploadFotoUsuarioSistema(multipartFile.getBytes(), nomeArquivo);
    }

    private boolean verificarExisteUsuario(Usuario usuario, ClienteSistema clienteSistema) {
        Optional<Usuario> optional = usuarioRepository.findByEmailAndClienteSistema(usuario.getEmail(),clienteSistema);
        return optional.isPresent();
    }

}
