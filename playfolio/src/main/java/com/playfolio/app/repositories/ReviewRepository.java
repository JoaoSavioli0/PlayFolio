package com.playfolio.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playfolio.app.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.jogoId = :idJogo and r.usuario.id = :idUsuario")
    Review reviewJaExistente(@Param("idJogo") int idJogo, @Param("idUsuario") Long idUsuario);

    @Query("select r from Review r join fetch r.usuario where r.jogoId = :idJogo")
    List<Review> findAllByJogoIdComUsuario(@Param("idJogo") int idJogo);

    Optional<Review> findByJogoIdAndUsuarioId(int jogoId, Long usuarioId);

    List<Review> findAllByUsuarioId(Long usuarioId);
}
