package mybuss.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pasajero")
public class Pasajero {
    // Mismo id del usuario (relación 1 a 1 con la tabla usuario)
    @Id
    @Column(name = "id_usuario", nullable = false, unique = true)
    private UUID idUsuario;
    @Column(nullable = false)
    private boolean notifActiva = true;

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isNotifActiva() {
        return notifActiva;
    }

    public void setNotifActiva(boolean notifActiva) {
        this.notifActiva = notifActiva;
    }

}
