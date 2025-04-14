package org.example.entities;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade")
public class DisponibilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @Column(name = "is_dia_todo")
    private boolean isDiaTodo;

    @Column(name = "is_bloqueado")
    private boolean isBloqueado;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public boolean isDiaTodo() {
        return isDiaTodo;
    }

    public void setDiaTodo(boolean diaTodo) {
        isDiaTodo = diaTodo;
    }

    public boolean isBloqueado() {
        return isBloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        isBloqueado = bloqueado;
    }
}
