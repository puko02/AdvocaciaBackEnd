package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditarDisponibilidadeSwing {

    public static void EditarDisponibilidade() {

        JFrame frmEditarDispo = new JFrame();
        frmEditarDispo.setSize(400, 300);
        frmEditarDispo.getContentPane().setLayout(null);
        frmEditarDispo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel diaSemanaPanel = new JPanel();
        diaSemanaPanel.setBounds(10, 10, 366, 85);
        frmEditarDispo.getContentPane().add(diaSemanaPanel);
        diaSemanaPanel.setLayout(new GridLayout(6, 6, 0, 0));

        JLabel dispobilidadeLabel = new JLabel("Disponibilidades de:");
        dispobilidadeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(dispobilidadeLabel);

        JLabel diaSemanaLabel = new JLabel("");
        diaSemanaLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(diaSemanaLabel);

        JLabel lblIdLabel = new JLabel("ID:");
        lblIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(lblIdLabel);

        JLabel idLabel = new JLabel("");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(idLabel);

        JLabel inicioLabel = new JLabel("Inicio:");
        inicioLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(inicioLabel);

        JLabel horaInicioLabel = new JLabel("");
        horaInicioLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(horaInicioLabel);

        JLabel fimLabel = new JLabel("Fim:");
        fimLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(fimLabel);

        JLabel horaFimLabel = new JLabel("");
        horaFimLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(horaFimLabel);

        JLabel todoLabel = new JLabel("Dia todo:");
        todoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(todoLabel);

        JLabel diaTodoLabel = new JLabel("");
        diaTodoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(diaTodoLabel);

        JLabel bloqueadoLabel = new JLabel("Bloqueado:");
        bloqueadoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(bloqueadoLabel);

        JLabel isBloqueadoLabel = new JLabel("");
        isBloqueadoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        diaSemanaPanel.add(isBloqueadoLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(88, 115, 215, 98);
        frmEditarDispo.getContentPane().add(buttonsPanel);
        buttonsPanel.setLayout(new BorderLayout(0, 0));

        JButton btnEditar = new JButton("Editar Disponibilidade");
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonsPanel.add(btnEditar, BorderLayout.NORTH);

        JButton btnRetornar = new JButton("Retornar");
        btnRetornar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmEditarDispo.dispose();
                DisponibilidadeFunction.menuDisponibilidade();
            }
        });
        btnRetornar.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonsPanel.add(btnRetornar, BorderLayout.SOUTH);

        frmEditarDispo.setVisible(true);
    }
}