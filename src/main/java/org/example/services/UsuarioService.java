package org.example.services;

import org.example.models.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final EntityManager em;

    public UsuarioService(EntityManager em) {
        this.em = em;
    }

    public Optional<UsuariosEntity> buscarPorEmail(String email) {
        TypedQuery<UsuariosEntity> query = em.createQuery(
                "SELECT u FROM UsuariosEntity u WHERE u.email = :email",
                UsuariosEntity.class
        );
        query.setParameter("email", email);

        List<UsuariosEntity> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}