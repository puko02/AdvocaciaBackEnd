package program.models;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "disponibilidade")
public class DisponibilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_disponibilidade")
    private Long id;

    @Column (name = "dia_semana")
    private String diaSemana;

    @Column (name = "hora_inicio")
    private Time horaInicio;

    @Column (name = "hora_fim")
    private Time horaFim;

    @Column (name = "is_dia_todo")
    private boolean isDiaTodo;

    @Column (name = "is_bloqueado")
    private boolean isBloqueado;

    public DisponibilidadeEntity(){}

    public DisponibilidadeEntity(String diaSemana, boolean isBloqueado, boolean isDiaTodo, String horaFim, String horaInicio) {
        this.diaSemana = diaSemana;
        this.isBloqueado = isBloqueado;
        this.isDiaTodo = isDiaTodo;
        this.horaFim = Time.valueOf(horaFim);
        this.horaInicio = Time.valueOf(horaInicio);
    }



    public Long getId() {
        return id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = Time.valueOf(horaInicio);
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = Time.valueOf(horaFim);
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
