package mybuss.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mybuss.dto.ActualizarUsuarioRequest;
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

    public Optional<Usuario> actualizarUsuario(UUID id, ActualizarUsuarioRequest datos) {
        Optional<Usuario> usuarioOpt = _usuarioRepository.findById(id);

        if (!usuarioOpt.isPresent()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();

        if (datos.getNombre() != null)
            usuario.setNombre(datos.getNombre());
        if (datos.getApellido() != null)
            usuario.setApellido(datos.getApellido());
        if (datos.getTipoDocumento() != null)
            usuario.setTipoDocumento(datos.getTipoDocumento().toUpperCase());
        if (datos.getNumeroDocumento() != null)
            usuario.setNumeroDocumento(datos.getNumeroDocumento());
        if (datos.getFechaNacimiento() != null)
            usuario.setFechaNacimiento(datos.getFechaNacimiento());
        if (datos.getCorreo() != null)
            usuario.setCorreo(datos.getCorreo());
        if (datos.getCelular() != null)
            usuario.setCelular(datos.getCelular());
        if (datos.getFotoUrl() != null)
            usuario.setFotoUrl(datos.getFotoUrl());
        if (datos.getRecibirNovedades() != null)
            usuario.setRecibirNovedades(datos.getRecibirNovedades());

        Usuario usuarioActualizado = _usuarioRepository.save(usuario);
        return Optional.of(usuarioActualizado);
    }

    public Boolean eliminarUsuario(UUID id) {
        if (!_usuarioRepository.existsById(id))
            return false;
        _usuarioRepository.deleteById(id);
        return true;
    }

}
