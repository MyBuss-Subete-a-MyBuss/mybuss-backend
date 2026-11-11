package mybuss.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mybuss.models.Pasajero;
import mybuss.models.Rol;
import mybuss.models.Usuario;
import mybuss.repository.PasajeroRepository;
import mybuss.repository.RolRepository;
import mybuss.repository.UsuarioRepository;

@Service
public class AuthService {

    private static final String ROL_REGISTRO = "PASAJERO";
    private static final List<String> TIPOS_DOCUMENTO = List.of("CC", "TI", "CE", "PPT", "PA");
    // Mínimo 8 caracteres, al menos una mayúscula y un número
    private static final String REGLA_CONTRASENA = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    final UsuarioRepository _usuarioRepo;
    final RolRepository _rolRepo;
    final PasajeroRepository _pasajeroRepo;
    final BCryptPasswordEncoder _encoder = new BCryptPasswordEncoder();

    AuthService(UsuarioRepository _usuarioRepo, RolRepository _rolRepo, PasajeroRepository _pasajeroRepo) {
        this._usuarioRepo = _usuarioRepo;
        this._rolRepo = _rolRepo;
        this._pasajeroRepo = _pasajeroRepo;
    }

    @Transactional
    public Usuario registrarUsuario(Usuario user) {
        validarCampoObligatorio(user.getNombre(), "El nombre es obligatorio.");
        validarCampoObligatorio(user.getApellido(), "El apellido es obligatorio.");
        validarCampoObligatorio(user.getTipoDocumento(), "El tipo de documento es obligatorio.");
        validarCampoObligatorio(user.getNumeroDocumento(), "El número de documento es obligatorio.");
        validarCampoObligatorio(user.getCorreo(), "El correo es obligatorio.");
        validarCampoObligatorio(user.getCelular(), "El celular es obligatorio.");
        validarCampoObligatorio(user.getContrasenaHash(), "La contraseña es obligatoria.");

        if (!TIPOS_DOCUMENTO.contains(user.getTipoDocumento()))
            throw new IllegalArgumentException(
                    "Tipo de documento no válido. Usa: " + String.join(", ", TIPOS_DOCUMENTO) + ".");
        if (user.getFechaNacimiento() == null || user.getFechaNacimiento().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de nacimiento no es válida.");
        if (!user.getContrasenaHash().matches(REGLA_CONTRASENA))
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.");
        if (!user.isAceptaTerminos())
            throw new IllegalArgumentException("Debes aceptar los términos y condiciones.");
        if (!user.isAutorizaDatos())
            throw new IllegalArgumentException("Debes autorizar el tratamiento de datos personales.");

        if (_usuarioRepo.existsByCorreoIgnoreCase(user.getCorreo()))
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
        if (_usuarioRepo.existsByNumeroDocumento(user.getNumeroDocumento()))
            throw new IllegalArgumentException("Ya existe un usuario con ese número de documento.");

        Rol pasajero = _rolRepo.findByNombre(ROL_REGISTRO)
                .orElseThrow(
                        () -> new IllegalStateException("No existe el rol " + ROL_REGISTRO + " en la base de datos."));

        user.setRol(pasajero);
        user.setContrasenaHash(_encoder.encode(user.getContrasenaHash()));
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user.setFechaAutorizacion(LocalDateTime.now());

        Usuario guardado = _usuarioRepo.save(user);

        Pasajero p = new Pasajero();
        p.setIdUsuario(guardado.getIdUsuario());
        _pasajeroRepo.save(p);

        return guardado;
    }

    public Optional<Usuario> iniciarSesion(String identificador, String contrasena) {
        if (identificador == null || contrasena == null)
            return Optional.empty();

        return _usuarioRepo.buscarParaLogin(identificador.trim())
                .filter(u -> _encoder.matches(contrasena, u.getContrasenaHash()));
    }

    private void validarCampoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException(mensaje);
    }

}
