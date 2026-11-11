package mybuss.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mybuss.models.Usuario;
import mybuss.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    final UsuarioService _usuarioService;

    UsuarioController(UsuarioService _usuarioService) {
        this._usuarioService = _usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") UUID id) {
        Optional<Usuario> usuario = _usuarioService.obtenerUsuarioPorId(id);
        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") UUID id) {
        try {
            boolean response = _usuarioService.eliminarUsuario(id);
            if (!response)
                return ResponseEntity.notFound().build();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
