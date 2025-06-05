package org.example.control.services;

import org.example.model.AgendamentoEntity;

import javax.persistence.EntityManager;

public class AgendamentoService {
    private final EntityManager em;

    public AgendamentoService(EntityManager em) {
        this.em = em;
    }

    public void salvarAgendamento(AgendamentoEntity agendamento) {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.persist(agendamento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
