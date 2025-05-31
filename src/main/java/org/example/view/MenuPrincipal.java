package org.example.view;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class MenuPrincipal {

    public static void mostrar(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1 - FAZER LOGIN ADM");
            System.out.println("2 - FAZER AGENDAMENTO");
            System.out.println("3 - VER SUA CONSULTA");
            System.out.println("0 - SAIR");

            System.out.print("--> ");
            int option = sc.nextInt();
            sc.nextLine(); //limpa

            switch (option) {
                case 1 -> MenuLoginFunction.Menulogin(em,sc);
                case 2 -> Agendamento.fazerAgendamento(em);
                case 3 -> MenuConsultasFunction.MenuConsultas(em,sc);
                case 0 -> {
                    System.out.println("Saindo...");
                    exit = true;
                }
                default -> System.out.println("Opção inválida.");
            }

        } while (!exit);
    }
}
