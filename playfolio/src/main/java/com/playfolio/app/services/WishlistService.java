package com.playfolio.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playfolio.app.dtos.WishlistDto;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.entities.Wishlist;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.playfolio.app.repositories.WishlistRepository;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    public boolean registraWishlistService(WishlistDto wishlistDto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(wishlistDto.getIdUsuario());

            Wishlist wishlist = new Wishlist();
            wishlist.setIdJogo(wishlistDto.getIdJogo());
            wishlist.setUsuario(usuario);

            return wishlistRepository.save(wishlist) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean verificaWishlistController(Long idUsuario, int idJogo) {
        return wishlistRepository.findByUsuarioIdAndIdJogo(idUsuario, idJogo).isPresent();
    }

    public List<WishlistDto> getWishlistUsuarioService(Long idUsuario) {
        return wishlistRepository.findByUsuarioId(idUsuario).stream()
                .map(w -> new WishlistDto(w.getId(), w.getUsuario().getId(), w.getIdJogo(), w.getDataInclusao()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Boolean> deleteWishlistUsuarioController(Long idWishlist) {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(idWishlist);
        if (wishlistOptional.isPresent())
            try {
                wishlistRepository.delete(wishlistOptional.get());
                return ResponseEntity.ok(true);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
