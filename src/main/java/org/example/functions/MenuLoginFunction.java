package org.example.functions;

import javax.persistence.EntityManager;
import java.awt.*;
import java.util.Scanner;

public class MenuLoginFunction {
    public static void Menulogin(EntityManager em, Scanner sc) {
        System.out.println("Menu Login\nDigite o e-mail: ");
        String email = sc.nextLine();
        System.out.println("Digite a senha: ");
        String senha = sc.nextLine();

        //Faz a busca na tabela de LoginADM
        //Verifica se o valor que foi inputado no sistema é o mesmo que está salvo no banco de dados
        //Faz a mesmsa verificação com a senha
        //Se ambos forem true, prosseguir para menu do Advogado, se não, retornar para o menu principal

        System.out.println("Acesso permitido\nMenu do Advogado:\n");
        MenuAdmin.menuAdministrador();
    }
}
