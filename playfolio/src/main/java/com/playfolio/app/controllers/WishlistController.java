package com.playfolio.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playfolio.app.dtos.WishlistDto;
import com.playfolio.app.services.WishlistService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.playfolio.app.entities.Wishlist;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;

    @PostMapping("/adiciona")
    public boolean registraWishlistController(@RequestBody WishlistDto wishlistDto) {
        return wishlistService.registraWishlistService(wishlistDto);
    }

    @GetMapping("/get")
    public boolean verificaWishlistController(@RequestParam Long idUsuario, @RequestParam int idJogo) {
        return wishlistService.verificaWishlistController(idUsuario, idJogo);
    }

    @GetMapping("/get/user")
    public List<WishlistDto> getWishlistUsuarioController(@RequestParam Long idUsuario) {
        return wishlistService.getWishlistUsuarioService(idUsuario);
    }

    @GetMapping("delete/{idWishlist}")
    public ResponseEntity<Boolean> deleteWishlistUsuarioController(@PathVariable Long idWishlist) {
        return wishlistService.deleteWishlistUsuarioController(idWishlist);
    }
}
