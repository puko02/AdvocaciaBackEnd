package program;

import javax.persistence.EntityManager;

import program.models.AgendamentoEntity;
import program.models.UsuariosEntity;
import program.repositories.CustomizerFactory;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        UsuariosEntity cliente = new UsuariosEntity();
        AgendamentoEntity agendamento1 = new AgendamentoEntity();

        cliente.setNome("Alexandro ");
        cliente.setTelefone("45999729416");
        cliente.setEmail("contato2@gmail.com");
        cliente.setSenha("506649");
        cliente.setAdmin(true);

        agendamento1.setCliente(cliente);
        agendamento1.setDescricao("Teste de agendamento...");
        agendamento1.setDataHora(LocalDateTime.of(2025, 04, 28, 14, 33));
        agendamento1.setStatus("Marcado");

        em.persist(cliente); // Persiste o objeto no banco de dados
        em.getTransaction().commit();
        em.close();
    }
}