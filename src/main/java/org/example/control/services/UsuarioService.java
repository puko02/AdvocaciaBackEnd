package org.example.control.services;

import org.example.model.UsuariosEntity;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsuarioService {
    private final EntityManager em;

    public UsuarioService(EntityManager em) {
        this.em = em;
    }

    private static final String CARACTERES = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public String gerarTokenConfirmacao() {
        StringBuilder token = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CARACTERES.length());
            token.append(CARACTERES.charAt(index));
        }
        return token.toString();
    }

    public void atualizarTokenConfirmacao(UsuariosEntity usuario) {
        String novoToken = gerarTokenConfirmacao();

        em.getTransaction().begin();
        usuario.setConfirmcode(novoToken);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Token de confirmação gerado e salvo: " + novoToken);
    }

    public Optional<UsuariosEntity> buscarPorEmail(String email) {
        TypedQuery<UsuariosEntity> query = em.createQuery(
                "SELECT u FROM UsuariosEntity u WHERE u.email = :email",
                UsuariosEntity.class
        );
        query.setParameter("email", email);
        List<UsuariosEntity> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<UsuariosEntity> listarClientes() {
        TypedQuery<UsuariosEntity> query = em.createQuery(
                "FROM UsuariosEntity", UsuariosEntity.class
        );
        return query.getResultList();
    }

    public void editarNome(UsuariosEntity usuario, Scanner sc) {
        System.out.println("Deseja alterar para qual nome?");
        String nomeNovo = sc.nextLine();
        if (nomeNovo.length() > 200) {
            System.out.println("ERRO: O nome não pode ter mais de 200 caractéres\n");
            return;
        }

        em.getTransaction().begin();
        usuario.setNome(nomeNovo);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Nome alterado para: " + nomeNovo + "\n");
    }

    public void editarTelefone(UsuariosEntity usuario, Scanner sc) {
        System.out.println("Deseja alterar para qual número de telefone? (11 dígitos)");
        String telefoneNovo = sc.nextLine();
        if (telefoneNovo.length() > 11 || telefoneNovo.length() < 10) {
            System.out.println("ERRO: O número deve ter entre 10 e 11 dígitos\n");
            return;
        }

        em.getTransaction().begin();
        usuario.setTelefone(telefoneNovo);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Telefone alterado para: " + telefoneNovo + "\n");
    }

    public void editarSenha(UsuariosEntity usuario, Scanner sc) {
        System.out.println("Deseja alterar para qual senha?");
        String senhaNova = sc.nextLine();
        if (BCrypt.checkpw(senhaNova, usuario.getSenha())) {
            System.out.println("ERRO: A nova senha não pode ser igual à atual!\n");
            return;
        }

        String senhaHashed = BCrypt.hashpw(senhaNova, BCrypt.gensalt());

        em.getTransaction().begin();
        usuario.setSenha(senhaHashed);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Senha alterada com sucesso.\n");
    }

    public void editarIsAdmin(UsuariosEntity usuario) {
        em.getTransaction().begin();
        usuario.setAdmin(!usuario.isAdmin());
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Admin status alterado para: " + (usuario.isAdmin() ? "ADMIN" : "USUÁRIO COMUM") + "\n");
    }

    public void excluirUsuario(UsuariosEntity usuario) {
        EntityTransaction transaction = em.getTransaction();

        if (usuario.isAdmin()) {
            System.out.println("Erro: não é permitido excluir um usuário com status de ADMIN.");
            return;
        }

        try {
            transaction.begin();

            // Exclui anotações vinculadas
            em.createQuery("DELETE FROM AnotacaoEntity a WHERE a.cliente.id = :id")
                    .setParameter("id", usuario.getId())
                    .executeUpdate();

            // Exclui agendamentos vinculados
            em.createQuery("DELETE FROM AgendamentoEntity a WHERE a.cliente.id = :id")
                    .setParameter("id", usuario.getId())
                    .executeUpdate();

            // Remove o usuário
            em.remove(usuario);

            transaction.commit();
            System.out.println("Usuário excluído com sucesso.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }
}
