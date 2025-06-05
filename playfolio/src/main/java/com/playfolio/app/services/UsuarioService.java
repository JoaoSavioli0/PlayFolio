package com.playfolio.app.services;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.playfolio.app.dtos.NewEmailDto;
import com.playfolio.app.dtos.NewSenhaDto;
import com.playfolio.app.dtos.RegistroDto;
import com.playfolio.app.dtos.UsuarioDto;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.exceptions.usuario.EmailJaCadastradoException;
import com.playfolio.app.repositories.ReviewRepository;
import com.playfolio.app.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReviewRepository reviewRepository;

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
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if(usuarioOptional.isPresent()){
            if(senha.equals(usuarioOptional.get().getSenha())){
                return usuarioOptional.get();
            }
        }
        return null;
    }

    public Usuario loginToken(String email){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        return usuarioOptional.isPresent() ? usuarioOptional.get() : null;
    }

    public UsuarioDto buscaPorUsernameService(String username){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuario(username);
        if(usuarioOptional.isPresent()){
            return reviewRepository.buscarUsuarioComNumReview(usuarioOptional.get().getId());
        }
        return null;
    }

    public UsuarioDto uploadFoto(Long id, MultipartFile foto){
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if(usuarioEncontrado.isPresent()){
            Usuario usuario = usuarioEncontrado.get();
            try{
                usuario.setImagem(foto.getBytes());
                usuarioRepository.save(usuario);
                return buscaPorUsernameService(usuario.getUsuario());
            } catch (java.io.IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getFoto(Long id){
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if(usuarioEncontrado.isPresent()){
            Usuario usuario = usuarioEncontrado.get();
            if(usuario.getImagem()!=null)
            return Base64.getEncoder().encodeToString(usuario.getImagem());
        }
        return "sem foto";
    }

    public ResponseEntity<?> atualizaSenhaService(NewSenhaDto newSenha){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(newSenha.getId());

        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            if(newSenha.senhaAtual.equals(usuario.getSenha())){
                usuario.setSenha(newSenha.getNovaSenha());
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Senha atualizada com sucesso!");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha atual incorreta!");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    public ResponseEntity<?> atualizaEmailService(NewEmailDto novoEmail){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(novoEmail.getId());

        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setEmail(novoEmail.email);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Email atualizado com sucesso!");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }
}
