package org.example.repositories;

import org.example.models.DisponibilidadeEntity;
import org.example.models.AnotacaoEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public interface IAnotacaoRepository {
    void adicionarNota(AnotacaoEntity anotacao);
    void adicionarAnotacao(Long idCliente, String conteudo);
    List<AnotacaoEntity> buscarAnotacoesPorCliente(Long idCliente);

    class DisponibilidadeRepository {

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
}
