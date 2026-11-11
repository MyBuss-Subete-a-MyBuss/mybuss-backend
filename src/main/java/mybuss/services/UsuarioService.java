package mybuss.services;

import mybuss.repository.UsuarioRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mybuss.models.Rol;
import mybuss.models.Usuario;

@Service
public class UsuarioService {

    final UsuarioRepository _usuarioRepository;

    UsuarioService(UsuarioRepository _usuarioRepository) {
        this._usuarioRepository = _usuarioRepository;
    }

    public Optional<Usuario> obtenerUsuarioPorId(UUID id) {
        return _usuarioRepository.findById(id);
    }

    public Boolean eliminarUsuario(UUID id) {
        if (!_usuarioRepository.existsById(id))
            return false;
        _usuarioRepository.deleteById(id);
        return true;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setContrasenaHash(usuario.getContrasenaHash());
        Rol rolPorDefecto = new Rol();
        rolPorDefecto.setIdRol(2);
        usuario.setRol(rolPorDefecto);
        usuario.setFechaRegistro(java.time.LocalDateTime.now()); 
        return _usuarioRepository.save(usuario);
    }

}
