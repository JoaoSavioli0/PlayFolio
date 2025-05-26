package com.playfolio.app.dtos;

public class WishlistDto {
    private Long idUsuario;
    private int idJogo;

    public WishlistDto(Long idUsuario, int idJogo){
        this.idUsuario = idUsuario;
        this.idJogo = idJogo;
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

    
}
