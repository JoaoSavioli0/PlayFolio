package com.playfolio.app.dtos;

import java.time.LocalDateTime;

public class ReviewDto {
    private Long idReview;
    private String texto;
    private Double nota;
    private int status;
    private int idJogo;
    private Long idUsuario;
    private LocalDateTime dataInclusao;

    public ReviewDto(int idJogo, Long idUsuario, String texto, Double nota, int status, LocalDateTime dataInclusao) {
        this.idJogo = idJogo;
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.nota = nota;
        this.status = status;
        this.dataInclusao = dataInclusao;
    }

    // Getters e Setters
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Long getIdReview() {
        return idReview;
    }

    public void setIdReview(Long idReview) {
        this.idReview = idReview;
    }

}
