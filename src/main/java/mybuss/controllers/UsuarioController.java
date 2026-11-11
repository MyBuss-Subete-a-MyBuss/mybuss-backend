package mybuss.controllers;

import java.util.Map;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import mybuss.models.Usuario;
import mybuss.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
    final UsuarioService _usuarioService;

    UsuarioController(UsuarioService _usuarioService) {
        this._usuarioService = _usuarioService;
    }

    //endpoint para registro
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Llama al servicio para procesar y guardar el usuario
            if (usuario.getContrasenaHash() == null) {
                // Forzamos a que contrasenaHash tome el valor que llegó mapeado
                usuario.setContrasenaHash(usuario.getContrasenaHash()); 
            }
            
            Usuario usuarioCreado = _usuarioService.registrarUsuario(usuario);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "¡Cuenta creada con éxito!");
            respuesta.put("id", usuarioCreado.getIdUsuario());

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al registrar el usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
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
