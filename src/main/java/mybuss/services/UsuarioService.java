package mybuss.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mybuss.models.Usuario;
import mybuss.repository.UsuarioRepository;

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

}
