package program.repositories;

import program.models.UsuariosEntity;
import java.util.List;

public interface IUsuariosRepository {
    List<UsuariosEntity> listarClientes();
}
