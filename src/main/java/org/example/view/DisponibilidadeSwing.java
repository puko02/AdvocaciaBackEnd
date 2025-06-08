package org.example.view;

import org.example.control.services.DisponibilidadeService;
import org.example.control.services.UsuarioService;
import org.example.model.DisponibilidadeEntity;
import org.example.control.repositories.*;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class DisponibilidadeSwing extends JFrame{

    private final DisponibilidadeService disponibilidadeService;
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeEntity disponibilidadeEntity;

    public DisponibilidadeSwing(EntityManager em){

        this.disponibilidadeService = new DisponibilidadeService(em);
        this.disponibilidadeRepository = new DisponibilidadeRepository(em);
        this.disponibilidadeEntity = new DisponibilidadeEntity(em);

        //JFrame frmMenuDisponibilidade = new JFrame();
        setTitle("Gerenciamento de Disponibilidade");
        setSize(375, 308);
        getContentPane().setLayout(null);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 10, 361, 30);
        getContentPane().add(titlePanel);

        JLabel titleLabel = new JLabel("Menu de Disponibilidade");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        titlePanel.add(titleLabel);

        JPanel diaSemanaPanel = new JPanel();
        diaSemanaPanel.setBounds(0, 62, 361, 67);
        getContentPane().add(diaSemanaPanel);

        JLabel diaSemanaLabel = new JLabel("Escolha o dia da semana a editar: ");
        diaSemanaLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        diaSemanaPanel.add(diaSemanaLabel);

        JComboBox comboBox = new JComboBox();
        comboBox.setToolTipText("Dia da Semana");
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Domingo", "Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado"}));
        comboBox.setMaximumRowCount(7);
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(comboBox);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditarDisponibilidadeSwing.abrirTela(em);
            }
        });
        btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 13));
        diaSemanaPanel.add(btnConfirmar);

        JPanel retornar = new JPanel();
        retornar.setBounds(0, 202, 361, 43);
        getContentPane().add(retornar);

        JButton btnRetornar = new JButton("Retornar");
        btnRetornar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        btnRetornar.setFont(new Font("Tahoma", Font.BOLD, 13));
        retornar.add(btnRetornar);

        /*
        Scanner sc = new Scanner(System.in);
        DisponibilidadeRepository disponibilidadeRepo = new DisponibilidadeRepository();

        System.out.println("Qual dia da semana deseja editar?");
        System.out.print(" 1 - Domingo\n 2 - Segunda-feira\n 3 - Terça-feira\n 4 - Quarta-feira\n 5 - Quinta-feira\n 6 - Sexta-feira\n 7 - Sábado\n  0 - Voltar ao menu do administrador\n -> ");
        int diaSelect = sc.nextInt();
        sc.nextLine();

        String diaSemana = getDiaSemana(diaSelect);
        if (diaSelect == 0){
            return;
        }

        if (diaSemana != null) {
            List<DisponibilidadeEntity> disponiveis = disponibilidadeRepo.diaDisponivel(diaSemana);

            if (disponiveis.isEmpty() || disponiveis == null) {
                System.out.println("Nenhuma disponibilidade cadastrada para " + diaSemana);
            } else {
                System.out.println("\n\tDisponibilidades de " + diaSemana + ":\n");
                for (DisponibilidadeEntity disp : disponiveis) {
                    System.out.println("ID: " + disp.getId() + "\nInício: " + disp.getHoraInicio() + "\nFim: " + disp.getHoraFim() +
                            "\nDia todo: " + disp.isDiaTodo() + "\nBloqueado: " + disp.isBloqueado());
                }

                System.out.println("Digite o ID da disponibilidade que deseja editar:");
                Long idEdit = sc.nextLong();
                sc.nextLine();

                System.out.println("Nova hora de início (00:00:00):");
                Time horaInicio = Time.valueOf(sc.nextLine());

                System.out.println("Nova hora de fim (00:00:00):");
                String horaFim = sc.nextLine();

                System.out.println("O dia todo? (true/false):");
                boolean isDiaTodo = sc.nextBoolean();

                System.out.println("Bloquear esse horário? (true/false):");
                boolean isBloqueado = sc.nextBoolean();

                // Atualizar no banco
                disponibilidadeRepo.atualizarDisponibilidade(idEdit, horaInicio, Time.valueOf(horaFim), isDiaTodo, isBloqueado);
                System.out.println("Disponibilidade atualizada com sucesso!");
            }
        } else {
            System.out.println("Opção inválida.");
        }

         */
    }

    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            DisponibilidadeSwing frmMenuDisponibilidade = new DisponibilidadeSwing(em);
            frmMenuDisponibilidade.setVisible(true);
        });
    }

    public static String getDiaSemana(int diaSelect) {
        switch (diaSelect) {
            case 1: return "domingo";
            case 2: return "segunda-feira";
            case 3: return "terça-feira";
            case 4: return "quarta-feira";
            case 5: return "quinta-feira";
            case 6: return "sexta-feira";
            case 7: return "sábado";
            default: return null;
        }
    }
}
