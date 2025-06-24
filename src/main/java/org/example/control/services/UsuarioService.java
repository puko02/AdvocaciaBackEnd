package org.example.control.services;

import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

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

    }

    public void enviarTokenEmail(String emailDestino) {
        Optional<UsuariosEntity> opt = buscarPorEmail(emailDestino);
        if (opt.isEmpty()) return;

        UsuariosEntity usuario = opt.get();
        String token = usuario.getConfirmcode();

        String remetente = "testepauliadvocacia@gmail.com";
        String senhaApp = "oktc btui lbdb lahi";

        String assunto = "Código de Verificação";
        String corpoHtml = """
    <!DOCTYPE html>
    <html lang="pt-BR">
    <head><meta charset="UTF-8"><title>Confirmação de E-mail</title></head>
    <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
        <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);">
            <h2 style="color: #333333;">Confirmação de E-mail</h2>
            <p style="font-size: 16px; color: #555555;">
                Olá, <strong>%s</strong>,
            </p>
            <p style="font-size: 16px; color: #555555;">
                Recebemos uma solicitação para verificar o seu endereço de e-mail.
            </p>
            <p style="font-size: 16px; color: #555555;">
                Seu código de verificação é:
            </p>
            <div style="text-align: center; margin: 24px 0;">
                <span style="display: inline-block; background-color: #7851b5; color: #ffffff; font-size: 24px; font-weight: bold; padding: 12px 24px; border-radius: 6px;">
                    %s
                </span>
            </div>
            <p style="font-size: 16px; color: #555555;">
                Insira esse código no sistema para ativar sua conta.
            </p>
            <p style="font-size: 14px; color: #999999;">
                Se você não solicitou essa verificação, ignore este e-mail.
            </p>
            <p style="font-size: 14px; color: #999999; margin-top: 30px;">
                Atenciosamente,<br>
                Equipe de Suporte
            </p>
        </div>
    </body>
    </html>
""".formatted(usuario.getNome(), token);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senhaApp);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject(assunto);
            message.setContent(corpoHtml, "text/html; charset=UTF-8");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();}
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

    public void salvarUsuario(UsuariosEntity usuario, String nome, String telefone, String email) {
        usuario.setNome(nome);
        usuario.setTelefone(telefone);
        usuario.setEmail(email);
        usuario.setSenha("");
        usuario.setAdmin(false);
        usuario.setActive(false);

        em.persist(usuario);
    }


    public void editarNome(UsuariosEntity usuario, String nomeNovo) {
        if (nomeNovo == null || nomeNovo.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        if (nomeNovo.length() > 200) {
            throw new IllegalArgumentException("ERRO: O nome não pode ter mais de 200 caracteres.");
        }

        em.getTransaction().begin();
        usuario.setNome(nomeNovo);
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void editarTelefone(UsuariosEntity usuario, String telefoneNovo) {
        if (telefoneNovo == null || telefoneNovo.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone não pode ser vazio.");
        }
        // Verifica se contém apenas dígitos
        if (!telefoneNovo.matches("\\d+")) {
            throw new IllegalArgumentException("ERRO: O telefone deve conter apenas números.");
        }
        if (telefoneNovo.length() < 10 || telefoneNovo.length() > 11) {
            throw new IllegalArgumentException("ERRO: O número deve ter entre 10 e 11 dígitos.");
        }

        em.getTransaction().begin();
        usuario.setTelefone(telefoneNovo);
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void editarSenha(UsuariosEntity usuario, String senhaNova) {
        if (senhaNova == null || senhaNova.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }

        if (senhaNova.equals(usuario.getSenha())) {
            throw new IllegalArgumentException("ERRO: A nova senha não pode ser igual à atual!");
        }


        em.getTransaction().begin();
        usuario.setSenha(senhaNova);
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void editarIsAdmin(UsuariosEntity usuario) {
        em.getTransaction().begin();
        usuario.setAdmin(!usuario.isAdmin()); // Inverte o status de admin
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void editarIsActive(UsuariosEntity usuario) {
        em.getTransaction().begin();
        usuario.setActive(!usuario.isActive()); // Inverte o status de ativo
        em.merge(usuario);
        em.getTransaction().commit();
    }


    public void excluirUsuario(UsuariosEntity usuario) {
        EntityTransaction transaction = em.getTransaction();

        if (usuario.isAdmin()) {
            System.out.println("Erro: não é permitido excluir um usuário com status de ADMIN.");
            return;
        }

        try {
            transaction.begin();

            em.createQuery("DELETE FROM AnotacaoEntity a WHERE a.cliente.id = :id")
                    .setParameter("id", usuario.getId())
                    .executeUpdate();

            em.createQuery("DELETE FROM AgendamentoEntity a WHERE a.cliente.id = :id")
                    .setParameter("id", usuario.getId())
                    .executeUpdate();

            em.remove(usuario);

            transaction.commit();
            System.out.println("Usuário excluído com sucesso.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public Optional<UsuariosEntity> buscarPorId(Long id) {
        UsuariosEntity usuario = em.find(UsuariosEntity.class, id);
        return Optional.ofNullable(usuario);
    }

}
