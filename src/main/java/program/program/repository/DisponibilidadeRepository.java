package org.example.repository;

import program.entities.DisponibilidadeEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class DisponibilidadeRepository {

    private final EntityManager em;

    public DisponibilidadeRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<DisponibilidadeEntity> findByDiaSemana(String diaSemana) {
        TypedQuery<DisponibilidadeEntity> query = em.createQuery(
                "SELECT d FROM DisponibilidadeEntity d WHERE LOWER(d.diaSemana) = :dia",
                DisponibilidadeEntity.class
        );
        query.setParameter("dia", diaSemana.toLowerCase());

        return query.getResultStream().findFirst();
    }
}
