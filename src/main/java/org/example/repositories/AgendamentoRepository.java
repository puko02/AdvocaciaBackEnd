package org.example.repositories;

import org.example.models.AgendamentoEntity;

import javax.persistence.EntityManager;

public class AgendamentoRepository {
    private EntityManager em;

    public AgendamentoEntity buscarPorId(Long id) {
        return em.find(AgendamentoEntity.class, id);
    }
}