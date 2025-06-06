package com.playfolio.app.dtos;

import java.time.LocalDateTime;

public class WishlistDto {
    private Long id;
    private Long idUsuario;
    private int idJogo;
    private LocalDateTime dataInclusao;

    public WishlistDto(Long id, Long idUsuario, int idJogo, LocalDateTime dataInclusao) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJogo = idJogo;
        this.dataInclusao = dataInclusao;
    }

    // Getters e Setters
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
