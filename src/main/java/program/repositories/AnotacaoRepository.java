package program.repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import program.config.HibernateUtil;
import program.models.AnotacaoEntity;
import program.models.UsuariosEntity;

import java.time.LocalDateTime;
import java.util.List;

public class AnotacaoRepository {

    public void adicionarNota(AnotacaoEntity anotacao) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(anotacao);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void adicionarAnotacao(Long idCliente, String conteudo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            UsuariosEntity cliente = session.get(UsuariosEntity.class, idCliente);
            if (cliente != null) {
                AnotacaoEntity anotacao = new AnotacaoEntity();
                anotacao.setCliente(cliente);
                anotacao.setNota(conteudo);
                anotacao.setDataCriacao(LocalDateTime.now());
                session.save(anotacao);
            } else {
                System.out.println("Cliente não encontrado.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<AnotacaoEntity> buscarAnotacoesPorCliente(Long idCliente) {
        List<AnotacaoEntity> anotacoes = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            anotacoes = session.createQuery(
                            "FROM AnotacaoEntity a WHERE a.cliente.id = :idCliente", AnotacaoEntity.class)
                    .setParameter("idCliente", idCliente)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return anotacoes;
    }
}
