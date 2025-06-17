package org.example.control.services;

import org.example.model.entities.UsuariosEntity;
import org.example.view.viewGUI.MenuAdmin;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

public class MenuLoginController {

    private final EntityManager em;

    public MenuLoginController(EntityManager em) {
        this.em = em;
    }

    public String validarLogin(String email, String senha) {
        try {
            String jpql = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
            TypedQuery<UsuariosEntity> query = em.createQuery(jpql, UsuariosEntity.class);
            query.setParameter("email", email);

            UsuariosEntity usuario = query.getSingleResult();

            if (!usuario.isAdmin()) {
                return "Sem permissão administrativa.";
            }

            if (BCrypt.checkpw(senha, usuario.getSenha())) {
                return "sucesso";
            } else {
                return "Senha incorreta.";
            }

        } catch (Exception e) {
            return "E-mail não encontrado.";
        }
    }

    public void abrirMenuAdmin() {
        MenuAdmin.mostrar(em);
    }
}