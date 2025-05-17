package org.example.functions;

import org.example.models.AgendamentoEntity;
import org.example.models.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class MenuConsultasFunction {
    public static void MenuConsultas(EntityManager em, Scanner sc){
        System.out.println("Menu de Consultas\nDigite o seu e-mail:");
        String email = sc.nextLine();
        System.out.println("Resultados da consulta pelo e-mail: " + email + "\n");

        String prefixoUsuario = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
        TypedQuery<UsuariosEntity> queryUsuario = em.createQuery(prefixoUsuario, UsuariosEntity.class);
        queryUsuario.setParameter("email", email);

        // Tentar buscar o usuário no banco de dados
        UsuariosEntity usuario = null;
        try {
            usuario = queryUsuario.getSingleResult();
        } catch (Exception e) {
            System.out.println("E-mail não encontrado. Retornando ao menu principal.");
            return;
        }

        String prefixoAgendamento = "SELECT a FROM AgendamentoEntity a WHERE a.cliente = :usuario ORDER BY a.dataHora DESC";
        TypedQuery<AgendamentoEntity> queryAgendamentos = em.createQuery(prefixoAgendamento, AgendamentoEntity.class);
        queryAgendamentos.setParameter("usuario", usuario);

        List<AgendamentoEntity> agendamentos = queryAgendamentos.getResultList();

        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado para o e-mail: " + email);
        } else {
            System.out.println("\nAgendamentos encontrados para o e-mail: " + email + ":\n");
            for (AgendamentoEntity agendamento : agendamentos) {
                System.out.println("ID: " + agendamento.getId());
                System.out.println("Data/Hora: " + agendamento.getDataHora());
                System.out.println("Status: " + agendamento.getStatus());
                System.out.println("Descrição: " + agendamento.getDescricao());
                System.out.println("---------------");
            }
        }

        System.out.println("\nPressione ENTER para retornar ao menu principal");
        sc.nextLine();
    }
}
