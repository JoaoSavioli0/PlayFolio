package com.playfolio.app.repositories;
import java.util.Optional;

import com.playfolio.app.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    public boolean existsByEmail(String email);
    public boolean existsByUsuario(String usuario);
    public Usuario findByEmail(String email);
}
