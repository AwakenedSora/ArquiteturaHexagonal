package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {

    private Long id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final LocalDate data;
    private final LocalTime hora;
    private final TipoConsulta tipo;
    private SituacaoConsulta situacao;
    private String observacoes;

    public Consulta(Long id, Animal animal, Veterinario veterinario,
                    LocalDate data, LocalTime hora, TipoConsulta tipo) {
        this.id = id;
        this.animal = animal;
        this.veterinario = veterinario;
        this.data = data;
        this.hora = hora;
        this.tipo = tipo;
        this.situacao = SituacaoConsulta.AGENDADA;
    }

    public void realizar(String observacoes) {
        if (this.situacao != SituacaoConsulta.AGENDADA) {
            throw new IllegalStateException(
                "Apenas consultas AGENDADAS podem ser realizadas. " +
                "Situacao atual: " + this.situacao);
        }
        this.situacao = SituacaoConsulta.REALIZADA;
        this.observacoes = observacoes;
    }

    public void cancelar() {
        if (this.situacao == SituacaoConsulta.CANCELADA) {
            throw new IllegalStateException(
                "Consulta ja esta CANCELADA. Nao e possivel cancelar novamente.");
        }
        this.situacao = SituacaoConsulta.CANCELADA;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Animal getAnimal() { return animal; }
    public Veterinario getVeterinario() { return veterinario; }
    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }
    public TipoConsulta getTipo() { return tipo; }
    public SituacaoConsulta getSituacao() { return situacao; }
    public String getObservacoes() { return observacoes; }
}
