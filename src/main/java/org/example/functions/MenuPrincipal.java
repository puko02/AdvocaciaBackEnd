package org.example.functions;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class MenuPrincipal {

    public static void mostrar(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1 - LOGIN ADM");
            System.out.println("2 - AGENDAMENTO");
            System.out.println("3 - SUA CONSULTA");
            System.out.println("0 - SAIR");

            System.out.print("--> ");
            int option = sc.nextInt();
            sc.nextLine(); //limpa

            switch (option) {
                case 1 -> System.out.println("Login ADM (placeholder)");
                case 2 -> Agendamento.fazerAgendamento(em);
                case 3 -> System.out.println("Sua consulta (placeholder)");
                case 0 -> {
                    System.out.println("Saindo...");
                    exit = true;
                }
                default -> System.out.println("Opção inválida.");
            }

        } while (!exit);
    }
}
