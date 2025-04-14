package com.playfolio.app.controllers;
import com.playfolio.app.exceptions.usuario.EmailJaCadastradoException;
import com.playfolio.app.exceptions.usuario.UsuarioJaCadastradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<String> EmailJaCadastradoHandler(EmailJaCadastradoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<String> UsuarioJaCadastradoHandler(UsuarioJaCadastradoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
