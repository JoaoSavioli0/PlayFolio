package com.playfolio.app.exceptions.usuario;

public class UsuarioJaCadastradoException extends RuntimeException{
    public UsuarioJaCadastradoException(String msg){
        super(msg);
    }
}
