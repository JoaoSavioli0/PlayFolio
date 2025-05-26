package com.playfolio.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playfolio.app.entities.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUsuarioIdAndIdJogo(Long idUsuario, int idJogo);
    List<Wishlist> findByUsuarioId(Long idUsuario); 
}
