package org.example.view;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class EditarDisponibilidadeSwing extends JFrame {


    public EditarDisponibilidadeSwing(EntityManager em) {

        //JFrame frmEditarDispo = new JFrame();
        setSize(400, 300);
        getContentPane().setLayout(null);

        JPanel diaSemanaPanel = new JPanel();
        diaSemanaPanel.setBounds(10, 10, 366, 85);
        getContentPane().add(diaSemanaPanel);
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
        getContentPane().add(buttonsPanel);
        buttonsPanel.setLayout(new BorderLayout(0, 0));

        JButton btnEditar = new JButton("Editar Disponibilidade");
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonsPanel.add(btnEditar, BorderLayout.NORTH);

        JButton btnRetornar = new JButton("Retornar");
        btnRetornar.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonsPanel.add(btnRetornar, BorderLayout.SOUTH);
    }

    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            EditarDisponibilidadeSwing frmEditarDispo = new EditarDisponibilidadeSwing(em);
            frmEditarDispo.setVisible(true);
        });
    }

}
