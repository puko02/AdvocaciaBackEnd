package org.example.functions;

import java.util.Scanner;

public class MenuAdmin {
    public static void menuAdministrador(){
        int opc = -1;
        Scanner sc = new Scanner(System.in);

        while(opc != 0) {
            System.out.println("Bem vindo ao menu de Administrador\n Escolha sua função");
            System.out.println("1 - Editar horário de atendimento do dia da semana");
            System.out.println("2 - Adicionar ou verificar notas de clientes");
            System.out.println("3 - Verificar agendamentos");
            System.out.print  ("0 - Sair do menu do administrador\n  -> ");
            int selected = sc.nextInt();
            sc.nextLine();

            switch(selected){
                case 1:
                    DisponibilidadeFunction.menuDisponibilidade();
                    break;
                case 2:
                    UsuariosFunction.menuUser();
                    break;
                case 3:

                    break;
                case 0:
                    System.out.println("Retornando...");
                    return;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }
}
