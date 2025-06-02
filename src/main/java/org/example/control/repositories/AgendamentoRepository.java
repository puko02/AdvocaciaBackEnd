package org.example.control.repositories;

import org.example.model.AgendamentoEntity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class AgendamentoRepository {

    public void atualizarAgendamento(Long id, LocalDateTime dataHora, String descricao, String status) {
        EntityManager em = org.example.control.repositories.CustomizerFactory.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            AgendamentoEntity agendamento = (AgendamentoEntity)em.find(AgendamentoEntity.class, id);
            if (agendamento != null) {
                agendamento.setDataHora(dataHora);
                agendamento.setDescricao(descricao);
                agendamento.setStatus(status);
                em.merge(agendamento);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public AgendamentoEntity buscarProximoAgendamento() {
        EntityManager em = CustomizerFactory.getEntityManager();

        try {
            TypedQuery<AgendamentoEntity> query = em.createQuery(
                    "FROM AgendamentoEntity a WHERE a.dataHora > :agora ORDER BY a.dataHora ASC",
                    AgendamentoEntity.class);
            query.setParameter("agora", LocalDateTime.now());
            query.setMaxResults(1);

            return query.getSingleResult(); // mais direto
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<AgendamentoEntity> listarAgendamentos() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.createQuery("FROM AgendamentoEntity", AgendamentoEntity.class).getResultList();
        } finally {
            em.close();
        }
    }



}
