package mybuss.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mybuss.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
    public abstract boolean existsByCorreoIgnoreCase(String correo);

    public abstract boolean existsByNumeroDocumento(String numeroDocumento);

    // Login: el usuario escribe su correo o su número de documento en el mismo campo
    @Query("SELECT u FROM Usuario u WHERE (LOWER(u.correo) = LOWER(:identificador) OR u.numeroDocumento = :identificador) AND u.activo = true")
    public abstract Optional<Usuario> buscarParaLogin(@Param("identificador") String identificador);
}
