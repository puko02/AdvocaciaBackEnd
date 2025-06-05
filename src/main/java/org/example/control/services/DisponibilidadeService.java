package org.example.control.services;

import org.example.model.DisponibilidadeEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class DisponibilidadeService {
    private final EntityManager em;

    public DisponibilidadeService(EntityManager em) {
        this.em = em;
    }

    public Optional<DisponibilidadeEntity> buscarPorDiaSemana(String diaSemana) {
        TypedQuery<DisponibilidadeEntity> query = em.createQuery(

                "SELECT d FROM DisponibilidadeEntity d WHERE LOWER(d.diaSemana) = :dia", DisponibilidadeEntity.class);
        //          SELECT * FROM disponibilidade
        //          WHERE LOWER(dia_semana) = 'segunda';

        query.setParameter("dia", diaSemana.toLowerCase());
        return query.getResultStream().findFirst();
    }
}
