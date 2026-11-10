package mybuss.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mybuss.models.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer> {
    public abstract Optional<Rol> findByNombre(String nombre);
}
