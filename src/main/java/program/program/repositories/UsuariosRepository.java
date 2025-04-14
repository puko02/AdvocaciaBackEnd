package program.repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import program.config.HibernateUtil;
import program.models.UsuariosEntity;

import java.util.List;

public class UsuariosRepository implements IUsuariosRepository {

    @Override
    public List<UsuariosEntity> listarClientes() {
        Transaction transaction = null;
        List<UsuariosEntity> clientes = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            clientes = session.createQuery("FROM UsuariosEntity", UsuariosEntity.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return clientes;
    }
}
