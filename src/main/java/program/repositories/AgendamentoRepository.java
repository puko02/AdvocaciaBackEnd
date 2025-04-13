
package program.repositories;


import program.models.AgendamentoEntity;
import program.models.DocumentosEntity;
import program.models.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Time;
import java.time.LocalTime;

public class AgendamentoRepository {

    /*public List<AgendamentoEntity> agendamentosCadastrados(UsuariosEntity cliente) {
        EntityManager em = CustomizerFactory.getEntityManager();
        TypedQuery<AgendamentoEntity> query = em.createQuery(
                "FROM AgendamentoEntity a WHERE a.cliente = :cliente ORDER BY a.dataHora", AgendamentoEntity.class);
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }*/

    public void atualizarAgendamento(Long id, LocalDateTime dataHora, List<DocumentosEntity> documentos, String descricao, UsuariosEntity cliente, String status) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            AgendamentoEntity agendamento = em.find(AgendamentoEntity.class, id);
            if (agendamento != null) {
                agendamento.setDataHora(dataHora);
                agendamento.setDocumentos(List.copyOf(documentos));
                agendamento.setDescricao(String.valueOf(descricao));
                agendamento.setCliente(cliente);
                agendamento.setStatus(status);

                em.merge(agendamento);  // Atualiza a entidade no banco
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