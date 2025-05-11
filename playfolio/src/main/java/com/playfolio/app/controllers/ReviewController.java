package com.playfolio.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.playfolio.app.dtos.ReviewDto;
import com.playfolio.app.services.ReviewService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.playfolio.app.entities.Review;
import java.util.List;


@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/new")
    public String registrarReviewController(@RequestBody ReviewDto review) {
        return reviewService.registraReviewService(review);
    }

    @GetMapping("/get/{idJogo}")
    public List<Review> getReviewsDoJogoController(@PathVariable int idJogo) {
        return reviewService.getReviewsDoJogoService(idJogo);
    }

    @GetMapping("/get")
    public ReviewDto getReviewDoUsuarioPorJogoController(@RequestParam int idJogo, @RequestParam Long idUsuario) {
        return reviewService.getReviewDoUsuarioPorJogoService(idJogo, idUsuario);
    }
}
