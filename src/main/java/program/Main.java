package program;

import javax.persistence.EntityManager;
import program.models.UsuariosEntity;
import program.repositories.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        UsuariosEntity cliente = new UsuariosEntity();

        em.getTransaction().commit();
        em.close();
    }
}