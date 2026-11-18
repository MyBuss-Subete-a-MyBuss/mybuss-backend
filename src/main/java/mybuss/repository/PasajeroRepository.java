package mybuss.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mybuss.models.Pasajero;

@Repository
public interface PasajeroRepository extends CrudRepository<Pasajero, UUID> {
}
