package org.example.control.services;

import org.example.model.entities.DisponibilidadeEntity;
import org.example.model.repositories.DisponibilidadeRepository;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.util.List;

public class DisponibilidadeController {

    private final DisponibilidadeRepository repo;

    public DisponibilidadeController(EntityManager em) {
        this.repo = new DisponibilidadeRepository(em);
    }

    public List<DisponibilidadeEntity> buscarPorDia(String diaSemana) {
        return repo.diaDisponivel(diaSemana);
    }

    public boolean atualizarDisponibilidade(Long id, Time horaInicio, Time horaFim, boolean isDiaTodo, boolean isBloqueado) {
        try {
            repo.atualizarDisponibilidade(id, horaInicio, horaFim, isDiaTodo, isBloqueado);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar disponibilidade: " + e.getMessage());
            return false;
        }
    }
}
