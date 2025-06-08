package com.playfolio.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.playfolio.app.dtos.LoginDto;
import com.playfolio.app.dtos.NewEmailDto;
import com.playfolio.app.dtos.NewSenhaDto;
import com.playfolio.app.dtos.RegistroDto;
import com.playfolio.app.dtos.UsuarioDto;
import com.playfolio.app.entities.Usuario;
import com.playfolio.app.repositories.ReviewRepository;
import com.playfolio.app.repositories.UsuarioRepository;
import com.playfolio.app.services.JwtService;
import com.playfolio.app.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> cadastroUsuarioController(@RequestBody RegistroDto usuario) {
        Usuario usuarioRegistrado = usuarioService.cadastraUsuarioService(usuario);
        if (usuarioRegistrado != null)
            return ResponseEntity.ok(usuarioRegistrado);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUsuarioController(@RequestBody LoginDto loginDto) {
        Usuario usuarioLogado = usuarioService.loginUsuarioService(loginDto.getEmail(), loginDto.getSenha());
        if (usuarioLogado != null)
            System.out.println("UsuarioLogado: " + usuarioLogado.getUsuario());
        else
            System.out.println("Usuario não encontrado");

        if (usuarioLogado != null) {
            UsuarioDto usuarioDto = usuarioService.buscaUsuarioDtoPorId(usuarioLogado.getId());
            String token = jwtService.generateToken(usuarioLogado.getEmail());

            Map<String, Object> response = new HashMap<>();

            response.put("usuario", usuarioDto);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou senha incorretas!");
    }

    @PostMapping("/foto-perfil/{id}/nova")
    public ResponseEntity<UsuarioDto> uploadFotoPerfil(@PathVariable Long id,
            @RequestParam("file") MultipartFile foto) {
        UsuarioDto usuarioDto = usuarioService.uploadFoto(id, foto);
        if (usuarioDto != null) {
            return ResponseEntity.ok(usuarioDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> validaToken(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");

            String email = jwtService.validateTokenAndGetUsername(jwt);

            Usuario usuario = usuarioService.loginToken(email);

            if (usuario != null) {
                UsuarioDto usuarioDto = usuarioService.buscaPorUsernameService(usuario.getUsuario());
                return ResponseEntity.ok(usuarioDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
    }

    @GetMapping("/fromUsername/{username}")
    public UsuarioDto buscaPorUsernameController(@PathVariable String username) {
        return usuarioService.buscaPorUsernameService(username);
    }

    @GetMapping("/all")
    public List<UsuarioDto> buscaTodosUsuarios() {
        return usuarioRepository.buscarTodosUsuariosComNumReview();
    }

    @PostMapping("/update-data")
    public ResponseEntity<?> atualizaDadosController(@RequestBody UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioAtualizadoOptional = usuarioRepository.findById(usuarioDto.getId());

        if (usuarioAtualizadoOptional.isPresent()) {
            Usuario usuarioAtualizado = usuarioAtualizadoOptional.get();

            if (usuarioRepository.findByUsuario(usuarioDto.getUsuario()).isPresent()
                    && !usuarioAtualizado.getUsuario().equals(usuarioDto.getUsuario()))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de usuário já cadastrado!");

            if (usuarioDto.getBio().length() > 0)
                usuarioAtualizado.setBio(usuarioDto.getBio());
            if (usuarioDto.getNome().length() > 0)
                usuarioAtualizado.setNome(usuarioDto.getNome());

            String usernameAtualizado = usuarioDto.getUsuario();
            if (usernameAtualizado.length() > 0) {
                usuarioAtualizado.setUsuario(usuarioDto.getUsuario());
            }

            try {
                usuarioRepository.save(usuarioAtualizado);
                UsuarioDto usuarioDtoAtualizado = usuarioService
                        .buscaPorUsernameService(usuarioAtualizado.getUsuario());

                return ResponseEntity.ok(usuarioDtoAtualizado);
            } catch (IllegalArgumentException error) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: Usuário logado não encontrado");
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> atualizaSenhaController(@RequestBody NewSenhaDto newSenha) {
        return usuarioService.atualizaSenhaService(newSenha);
    }

    @PostMapping("/update-email")
    public ResponseEntity<?> atualizaSenhaController(@RequestBody NewEmailDto newEmail) {
        return usuarioService.atualizaEmailService(newEmail);
    }
}
