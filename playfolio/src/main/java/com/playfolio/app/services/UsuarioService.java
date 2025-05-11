package com.playfolio.app.services;

import com.playfolio.app.exceptions.usuario.EmailJaCadastradoException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public void cadastraUsuarioService(Usuario usuario){
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new EmailJaCadastradoException("Esse email já está registrado!");
        }
        if(usuarioRepository.existsByUsuario(usuario.getUsuario())){
            throw new EmailJaCadastradoException("Esse apelido já está registrado!");    
        }
        usuarioRepository.save(usuario);
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
