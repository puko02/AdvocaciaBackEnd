package org.example.control.services;

import org.example.model.entities.AgendamentoEntity;
import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MenuConsultasController {

    private final EntityManager em;

    public MenuConsultasController(EntityManager em) {
        this.em = em;
    }

    public UsuariosEntity buscarUsuarioPorEmail(String email) throws Exception {
        String jpql = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
        TypedQuery<UsuariosEntity> query = em.createQuery(jpql, UsuariosEntity.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public List<AgendamentoEntity> buscarAgendamentosPorUsuario(UsuariosEntity usuario) {
        String jpql = "SELECT a FROM AgendamentoEntity a WHERE a.cliente = :usuario ORDER BY a.dataHora DESC";
        TypedQuery<AgendamentoEntity> query = em.createQuery(jpql, AgendamentoEntity.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
}