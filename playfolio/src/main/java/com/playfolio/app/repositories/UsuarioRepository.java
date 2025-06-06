package com.playfolio.app.repositories;

import java.util.Optional;

import com.playfolio.app.dtos.UsuarioDto;
import com.playfolio.app.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public boolean existsByEmail(String email);

    public boolean existsByUsuario(String usuario);

    public Optional<Usuario> findByEmail(String email);

    public Optional<Usuario> findByUsuario(String usuario);

    @Query("SELECT new com.playfolio.app.dtos.UsuarioDto(u.id, u.nome, u.email, u.usuario, u.bio, u.imagem, 0L) " +
            "FROM Usuario u " +
            "WHERE u.id = :idUsuario")
    UsuarioDto buscarUsuarioDtoPorId(@Param("idUsuario") Long idUsuario);
}
