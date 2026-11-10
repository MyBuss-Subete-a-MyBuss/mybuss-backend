package mybuss.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import mybuss.models.Usuario;
import mybuss.repository.UsuarioRepository;

@Service
public class AuthService {

    final UsuarioRepository _usuarioRepo;

    AuthService(UsuarioRepository _usuarioRepo) {
        this._usuarioRepo = _usuarioRepo;
    }

    public Usuario registrarUsuario(Usuario user) {
        validarCampoObligatorio(user.getNombre(), "El nombre es obligatorio.");
        validarCampoObligatorio(user.getApellido(), "El apellido es obligatorio.");
        validarCampoObligatorio(user.getCorreo(), "El correo es obligatorio.");
        validarCampoObligatorio(user.getContrasena(), "La contraseña es obligatoria.");

        if (_usuarioRepo.findByCorreo(user.getCorreo()).isPresent())
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");

        return _usuarioRepo.save(user);
    }

    public Optional<Usuario> iniciarSesion(String correo, String contrasena) {
        Optional<Usuario> usuario = _usuarioRepo.findByCorreo(correo);
        if (usuario.isEmpty())
            return Optional.empty();
        if (!usuario.get().getContrasena().equals(contrasena))
            return Optional.empty();

        return usuario;
    }

    private void validarCampoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException(mensaje);
    }

}
