package org.example.control.repositories;

import org.example.model.DisponibilidadeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.sql.Time;
import java.time.LocalTime;

public class DisponibilidadeRepository {

    public List<DisponibilidadeEntity> diaDisponivel(String diaSemana) {
        EntityManager em = CustomizerFactory.getEntityManager();
        TypedQuery<DisponibilidadeEntity> query = em.createQuery(
                "SELECT disp FROM DisponibilidadeEntity disp WHERE disp.diaSemana = :diaSemana",
                DisponibilidadeEntity.class
        );
        query.setParameter("diaSemana", diaSemana);
        return query.getResultList();
    }

    public void atualizarDisponibilidade(Long id, Time horaInicio, Time horaFim, boolean isDiaTodo, boolean isBloqueado) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            DisponibilidadeEntity disponibilidade = em.find(DisponibilidadeEntity.class, id);
            if (disponibilidade != null) {
                disponibilidade.setHoraInicio(String.valueOf(horaInicio));
                disponibilidade.setHoraFim(String.valueOf(horaFim));
                disponibilidade.setDiaTodo(isDiaTodo);
                disponibilidade.setBloqueado(isBloqueado);

                em.merge(disponibilidade);  // Atualiza a entidade no banco
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
