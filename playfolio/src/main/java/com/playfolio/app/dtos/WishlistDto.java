package com.playfolio.app.dtos;

import java.time.LocalDateTime;

public class WishlistDto {
    private Long idUsuario;
    private int idJogo;
    private LocalDateTime dataInclusao;

    public WishlistDto(Long idUsuario, int idJogo, LocalDateTime dataInclusao){
        this.idUsuario = idUsuario;
        this.idJogo = idJogo;
        this.dataInclusao = dataInclusao;
    }

    //Getters e Setters
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public int getIdJogo() {
        return idJogo;
    }
    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    
}
