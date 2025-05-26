package com.playfolio.app.controllers;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.services.UsuarioService;
import com.playfolio.app.dtos.LoginDto;
import com.playfolio.app.services.JwtService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.playfolio.app.dtos.RegistroDto;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> cadastroUsuarioController(@RequestBody RegistroDto usuario) {
        Usuario usuarioRegistrado = usuarioService.cadastraUsuarioService(usuario);
        if(usuarioRegistrado!=null) return ResponseEntity.ok(usuarioRegistrado); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public Object loginUsuarioController(@RequestBody LoginDto loginDto) {
        Usuario usuarioLogado = usuarioService.loginUsuarioService(loginDto.getEmail(), loginDto.getSenha());
        if(usuarioLogado!=null){
            String token = jwtService.generateToken(usuarioLogado.getEmail());

            Map<String, Object> response = new HashMap<>();

            response.put("usuario", usuarioLogado);
            response.put("token", token);

            return response;
        }
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<?> validaToken(@RequestHeader("Authorization") String token){
        try {
            String jwt = token.replace("Bearer ", "");
    
            String email = jwtService.validateTokenAndGetUsername(jwt);
    
            Usuario usuario = usuarioService.loginToken(email);
    
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
    }
}
