package com.playfolio.app.services;

import com.playfolio.app.exceptions.usuario.EmailJaCadastradoException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playfolio.app.dtos.RegistroDto;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario cadastraUsuarioService(RegistroDto usuarioDto){

        if(usuarioRepository.existsByEmail(usuarioDto.getEmail())){
            throw new EmailJaCadastradoException("Esse email já está registrado!");
        }
        if(usuarioRepository.existsByUsuario(usuarioDto.getUsuario())){
            throw new EmailJaCadastradoException("Esse apelido já está registrado!");    
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNome(usuarioDto.getNome());
        usuario.setSenha(usuarioDto.getSenha());
        usuario.setUsuario(usuarioDto.getUsuario());

        return usuarioRepository.save(usuario);
    }

    public Usuario loginUsuarioService(String email, String senha){
        Usuario usuarioLogado = usuarioRepository.findByEmail(email);
        if(usuarioLogado!=null){
            if(senha.equals(usuarioLogado.getSenha())){
                return usuarioLogado;
            }
        }
        return null;
    }

    public Usuario loginToken(String email){
        return usuarioRepository.findByEmail(email);
    }
}
