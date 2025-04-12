package program;

import javax.persistence.EntityManager;
import program.models.UsuariosEntity;
import program.repositories.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        UsuariosEntity cliente = new UsuariosEntity();

        cliente.setNome("Alexandro ");
        cliente.setTelefone("45999729416");
        cliente.setEmail("contato2@gmail.com");
        cliente.setSenha("506649");
        cliente.setAdmin(true);

        em.persist(cliente); // Persiste o objeto no banco de dados
        em.getTransaction().commit();
        em.close();
    }
}