package org.example.repositories;

import org.example.models.AgendamentoEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class AgendamentoRepository {
    private EntityManager em;

    public AgendamentoEntity buscarPorId(Long id) {
        return em.find(AgendamentoEntity.class, id);
    }

    public void atualizarAgendamento(Long id, LocalDateTime dataHora, String descricao, String status) {
        EntityManager em = CustomizerFactory.getEntityManager();
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

}
