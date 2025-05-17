package org.example.view;

import org.example.model.DisponibilidadeEntity;
import org.example.control.repositories.*;

import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class DisponibilidadeFunction {

    public static void menuDisponibilidade(){
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

                System.out.println("Nova hora de início:");
                Time horaInicio = Time.valueOf(sc.nextLine());

                System.out.println("Nova hora de fim:");
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
