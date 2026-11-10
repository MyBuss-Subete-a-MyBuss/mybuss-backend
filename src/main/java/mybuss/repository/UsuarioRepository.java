package mybuss.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mybuss.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    public abstract Optional<Usuario> findByCorreo(String correo);
}
