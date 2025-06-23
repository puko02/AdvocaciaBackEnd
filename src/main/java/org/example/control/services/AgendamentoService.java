package org.example.control.services;

import org.example.model.entities.AgendamentoEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

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
    public Optional<AgendamentoEntity> procurarAgendamentoPorHorario(LocalDateTime dataHora) {
        TypedQuery<AgendamentoEntity> query = em.createQuery(
                "SELECT a FROM AgendamentoEntity a WHERE a.dataHora = :dataHora",
                AgendamentoEntity.class
        );
        query.setParameter("dataHora", dataHora);
        return query.getResultStream().findFirst(); // Optional vazio se não houver
    }
}
