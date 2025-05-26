package com.playfolio.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playfolio.app.dtos.ReviewDto;
import com.playfolio.app.entities.Review;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.repositories.ReviewRepository;
import com.playfolio.app.repositories.UsuarioRepository;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public String registraReviewService(ReviewDto review){
        if(reviewRepository.reviewJaExistente(review.getIdJogo(), review.getIdUsuario()) != null) return "Erro: review de usuário para jogo já existe.";

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(review.getIdUsuario()); 
        if(usuarioOptional.isEmpty()) return "Erro: usuário não encontrado";

        Usuario usuario = usuarioOptional.get();

        Review novaReview = new Review();
        novaReview.setJogoId(review.getIdJogo());
        novaReview.setUsuario(usuario);
        novaReview.setNota(review.getNota());
        novaReview.setStatus(review.getStatus());
        novaReview.setReview(review.getTexto());

        try{
            reviewRepository.save(novaReview);
            return "Review cadastrada com sucesso.";
        } catch (Exception e){
            System.out.println("Erro: erro ao salvar review no banco de dados: " + e.getMessage());
        }

        return "Erro: erro desconhecido";
    }

    public String atualizaReviewService(ReviewDto reviewDto){
        System.out.println(reviewDto.getIdJogo() + " " + reviewDto.getIdUsuario());
        Optional<Review> reviewOptional = reviewRepository.findByJogoIdAndUsuarioId(reviewDto.getIdJogo(), reviewDto.getIdUsuario());
        if(reviewOptional.isPresent()){
            Review review = reviewOptional.get();
            review.setNota(reviewDto.getNota());
            review.setReview(reviewDto.getTexto());
            review.setStatus(reviewDto.getStatus());

            if(reviewRepository.save(review)!=null){
                System.out.println("Editou");
                return "Review atualizada com sucesso.";
            }
        }
        return "Erro desconhecido";
    }

    public List<Review> getReviewsDoJogoService(int idJogo){
        return reviewRepository.findAllByJogoIdComUsuario(idJogo);
    }

    public List<ReviewDto> getReviewsDoUsuarioService(Long idUsuario){
        return reviewRepository.findAllByUsuarioId(idUsuario).stream()
        .map(r -> new ReviewDto(r.getJogoId(), r.getUsuario().getId(), r.getReview(), r.getNota(), r.getStatus()))
        .collect(Collectors.toList());
    }

    public ReviewDto getReviewDoUsuarioPorJogoService(int idJogo, Long idUsuario){
        Optional<Review> reviewOptional = reviewRepository.findByJogoIdAndUsuarioId(idJogo, idUsuario);
        if(reviewOptional.isEmpty()) return null;

        Review reviewEncontrada = reviewOptional.get();
        return new ReviewDto(idJogo, idUsuario, reviewEncontrada.getReview(), reviewEncontrada.getNota(), reviewEncontrada.getStatus());
    }

    public void deleteReviewService(Long idReview){
        Optional<Review> reviewOptional = reviewRepository.findById(idReview);
        if(reviewOptional.isPresent()) reviewRepository.delete(reviewOptional.get());
    }
}
