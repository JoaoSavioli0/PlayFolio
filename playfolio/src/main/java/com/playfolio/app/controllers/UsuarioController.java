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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<?> cadastroUsuarioController(@RequestBody Usuario usuario) {
        usuarioService.cadastraUsuarioService(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
}
