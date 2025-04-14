package org.example.repository;

import program.entities.AgendamentoEntity;

import javax.persistence.EntityManager;

public class AgendamentoRepository {
    private EntityManager em;

    public AgendamentoEntity buscarPorId(Long id) {
        return em.find(AgendamentoEntity.class, id);
    }
}