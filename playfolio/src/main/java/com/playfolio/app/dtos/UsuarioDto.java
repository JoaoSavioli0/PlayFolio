package com.playfolio.app.dtos;

public class UsuarioDto {
    private Long Id;
    private String nome;
    private String usuario;
    private String email;
    private String bio;
    private Long numJogos;
    private byte[] imagem;

    public UsuarioDto(Long id, String nome, String email, String usuario, String bio, byte[] imagem, Long numJogos){
        this.Id = id;
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.bio = bio;
        this.imagem = imagem;
        this.numJogos = numJogos;
    }

    //Getters e Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public Long getNumJogos() {
        return numJogos;
    }
    public void setNumJogos(Long numJogos) {
        this.numJogos = numJogos;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getImagem() {
        return imagem;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
