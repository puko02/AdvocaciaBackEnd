package program.functions;

import java.util.Scanner;

public class MenuConsultasFunction {
    public static void MenuConsultas(Scanner sc){
        System.out.println("Menu de Consultas\nDigite o seu e-mail:");
        String email = sc.nextLine();
        System.out.println(email);
        System.out.println("Resultados da consulta pelo email: " + email + "\n\n");

        //Mostrará as reuniões que estão vinculadas ao email digitado, ordenadas pelo status (agendado, concluido)
        System.out.println("\nPressione ENTER para retornar ao menu principal");
        sc.nextLine();
    }
}
