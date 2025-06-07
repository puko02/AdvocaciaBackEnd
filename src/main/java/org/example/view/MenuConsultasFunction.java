package org.example.view;

import org.example.model.AgendamentoEntity;
import org.example.model.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class MenuConsultasFunction {

    private static JTextField txtEmailemailcom;

    public static void MenuConsultas(EntityManager em, Scanner sc){

            JFrame frmMenu = new JFrame();
            frmMenu.setTitle("Menu de Consultas");
            frmMenu.setSize(400, 400);
            frmMenu.getContentPane().setLayout(null);

            JLabel title = new JLabel("Menu de Consultas por Usuário");
            title.setFont(new Font("Tahoma", Font.BOLD, 16));
            title.setBounds(65, 10, 260, 30);
            frmMenu.getContentPane().add(title);

            JPanel procurarConsultaUsuario = new JPanel();
            procurarConsultaUsuario.setBounds(10, 50, 366, 80);
            frmMenu.getContentPane().add(procurarConsultaUsuario);

            JLabel emailUserLabel = new JLabel("Email do Usuário :");
            emailUserLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            procurarConsultaUsuario.add(emailUserLabel);

            txtEmailemailcom = new JTextField();
            txtEmailemailcom.setForeground(Color.DARK_GRAY);
            txtEmailemailcom.setBackground(Color.WHITE);
            txtEmailemailcom.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtEmailemailcom.setText("email@email.com");
            procurarConsultaUsuario.add(txtEmailemailcom);
            txtEmailemailcom.setColumns(15);

            JButton btnProcurarConsulta = new JButton("Procurar Consulta");
            btnProcurarConsulta.setForeground(Color.DARK_GRAY);
            btnProcurarConsulta.setFont(new Font("Tahoma", Font.BOLD, 13));
            btnProcurarConsulta.setBackground(UIManager.getColor("Button.background"));
            procurarConsultaUsuario.add(btnProcurarConsulta);

            JPanel consultaUsuario = new JPanel();
            consultaUsuario.setBounds(10, 140, 366, 155);
            frmMenu.getContentPane().add(consultaUsuario);

            JPanel botaoSair = new JPanel();
            botaoSair.setBounds(10, 313, 366, 30);
            frmMenu.getContentPane().add(botaoSair);

            JButton btnSair = new JButton("Retornar");
            btnSair.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frmMenu.dispose();
                }
            });
            btnSair.setForeground(Color.DARK_GRAY);
            btnSair.setFont(new Font("Tahoma", Font.BOLD, 13));
            botaoSair.add(btnSair);

            frmMenu.setVisible(true);

            /*
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
             */
    }
}
