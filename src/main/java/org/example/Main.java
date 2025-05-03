package org.example;

import javax.persistence.EntityManager;

import org.example.functions.MenuPrincipal;
import org.example.models.UsuariosEntity;
import org.example.repositories.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();

        MenuPrincipal.mostrar(em);

        em.close();
    }
}