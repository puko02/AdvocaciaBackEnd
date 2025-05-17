package org.example.repositories;

import org.example.models.UsuariosEntity;
import java.util.List;

public interface IUsuariosRepository {
    List<UsuariosEntity> listarClientes();
}
