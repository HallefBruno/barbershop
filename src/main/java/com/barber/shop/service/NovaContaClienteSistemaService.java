
package com.barber.shop.service;

import com.barber.shop.exception.NegocioException;
import com.barber.shop.model.ClienteSistema;
import com.barber.shop.model.Grupo;
import com.barber.shop.model.Usuario;
import com.barber.shop.model.ValidarCliente;
import com.barber.shop.repository.ClienteSistemaRepository;
import com.barber.shop.repository.UsuarioRepository;
import com.barber.shop.repository.ValidarClienteRepository;
import com.barber.shop.util.StorageCloudnary;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class NovaContaClienteSistemaService {

    private final ValidarClienteRepository clienteRepository;
    private final ClienteSistemaRepository clienteSistemaRepository;
    private final UsuarioRepository usuarioRepository;
    private final StorageCloudnary storageCloudnary;
    private final PasswordEncoder passwordEncoder;
    private final GrupoService grupoService;
    private final static String SUPER_USER = "SuperUser";

    @Transactional
    public void salvaValidarCliente(String cpfCnpj) {
        Optional<ValidarCliente> temCnpj = clienteRepository.findByCpfCnpj(cpfCnpj);
        if (temCnpj.isPresent() && temCnpj.get().getContaCriada()) {
            throw new NegocioException(HttpStatus.BAD_REQUEST,"Esse cliente já possui conta!");
        } else if (!temCnpj.isPresent()) {
            verificaSeClienteEstaCadastrado(cpfCnpj);
        }
    }

    private void verificaSeClienteEstaCadastrado(String cpfCnpj) {
        Optional<ClienteSistema> opClienteSistema = clienteSistemaRepository.findByCpfCnpj(cpfCnpj);
        if (opClienteSistema.isPresent()) {
            ClienteSistema clienteSistema = opClienteSistema.get();
            if (clienteSistema.getPrimeiroAcesso() && clienteSistema.getAcessarTelaCriarLogin()) {
                ValidarCliente validarCliente = new ValidarCliente();
                validarCliente.setCpfCnpj(cpfCnpj);
                validarCliente.setDataValidacao(LocalDateTime.now());
                clienteRepository.save(validarCliente);
            }
        } else {
            throw new NegocioException(HttpStatus.BAD_REQUEST,"Cliente sem permisão para criar conta!");
        }
    }
    
    @Transactional
    public void criarPreContaUsuarioClienteSistema(MultipartFile multipartFile, Usuario usuario) {
        if(StringUtils.isBlank(usuario.getCpfCnpj())) {
            throw new NegocioException(HttpStatus.BAD_REQUEST,"CPF CNPJ inválido!");
        }
        usuario.setCpfCnpj(StringUtils.getDigits(usuario.getCpfCnpj()));
        Optional<ClienteSistema> clienteSistemaOptional = clienteSistemaRepository.findByCpfCnpj(usuario.getCpfCnpj());
        clienteSistemaOptional.map(clienteSistema -> {
            if(criarConta(clienteSistema)) {
                if(Objects.isNull(multipartFile)) {
                    throw new NegocioException(HttpStatus.BAD_REQUEST,"É necessário selecionar a foto!");
                }
                usuarioRepository.findByEmailAndClienteSistema(usuario.getEmail(), clienteSistema).ifPresent((u) -> {
                    throw new NegocioException(HttpStatus.BAD_REQUEST,"Já existe um usuário com esse e-mail!");
                });
                try {
                    String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                    usuario.setNomeFoto(UUID.randomUUID().toString());
                    usuario.setTelefone(StringUtils.getDigits(usuario.getTelefone()));
                    usuario.setExtensao(extension);
                    usuario.setClienteSistema(clienteSistema);
                    usuario.setAtivo(Boolean.TRUE);
                    usuario.setProprietario(Boolean.TRUE);
                    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
                    usuario.setGrupos(getGrupos());
                    usuarioRepository.save(usuario);
                    storageCloudnary.uploadFoto(multipartFile.getBytes(), usuario.getClienteSistema().getPastaPrincipal().concat("/").concat(usuario.getCpfCnpj()).concat("/").concat(usuario.getClienteSistema().getPastaImagensUsuarioSistema()));
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                    throw new NegocioException(HttpStatus.BAD_REQUEST,"Não foi possível fazer o upload da imagem!");
                }
            }
            return Void.TYPE;
        }).orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND,"Nenhum cliente cadastrado com esse CPF/CNPJ!"));
    }
    
    private Set<Grupo> getGrupos() {
        List<Grupo> grupoSelecionado = grupoService.grupos().stream().filter(g -> !g.getNome().equalsIgnoreCase(SUPER_USER)).collect(Collectors.toList());
        Set<Grupo> grupos = new LinkedHashSet<>(grupoSelecionado);
        return grupos;
    }

    private boolean criarConta(ClienteSistema clienteSistema) {
        return BooleanUtils.isTrue(clienteSistema.getAtivo()) && BooleanUtils.isTrue(clienteSistema.getAcessarTelaCriarLogin()) && BooleanUtils.isTrue(clienteSistema.getPrimeiroAcesso());
    }

}
