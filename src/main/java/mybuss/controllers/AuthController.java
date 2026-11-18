package mybuss.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mybuss.dto.LoginRequest;
import mybuss.models.Usuario;
import mybuss.services.AuthService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
    final AuthService _authService;

    AuthController(AuthService _authService) {
        this._authService = _authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario u) {
        try {
            Usuario usuario = _authService.registrarUsuario(u);
            return ResponseEntity.status(201).body(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> iniciarSesion(@RequestBody LoginRequest login) {
        try {
            Optional<Usuario> usuario = _authService.iniciarSesion(login.getIdentificador(), login.getContrasena());
            if (usuario.isEmpty()) {
                return ResponseEntity.status(401).build();
            }
            return ResponseEntity.ok(usuario.get());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
