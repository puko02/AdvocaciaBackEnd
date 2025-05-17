package org.example.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "agendamento")
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private UsuariosEntity cliente;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String status;

    private String descricao;

    public AgendamentoEntity(String nome, String telefone, String email, LocalDateTime dataHora) {
    }

    public AgendamentoEntity() {

    }

    public List<DocumentosEntity> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentosEntity> documentos) {
        this.documentos = documentos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public UsuariosEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuariosEntity cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    @ManyToMany
    @JoinTable(
            name = "documento_agendamento",
            joinColumns = @JoinColumn(name = "id_agendamento"),
            inverseJoinColumns = @JoinColumn(name = "id_documento")
    )
    private List<DocumentosEntity> documentos;
}