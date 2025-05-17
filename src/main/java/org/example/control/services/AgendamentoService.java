package org.example.control.services;

import org.example.model.AgendamentoEntity;

import javax.persistence.EntityManager;

public class AgendamentoService {
    private final EntityManager em;

    public AgendamentoService(EntityManager em) {
        this.em = em;
    }

    public void salvarAgendamento(AgendamentoEntity agendamento) {
        em.getTransaction().begin();
        em.persist(agendamento);
        em.getTransaction().commit();
    }
}
