package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Animal {

    private Long id;
    private final String nome;
    private final String especie;
    private final String raca;
    private final LocalDate dataNascimento;
    private final String tutor;

    public Animal(Long id, String nome, String especie, String raca,
                  LocalDate dataNascimento, String tutor) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do animal nao pode ser nulo ou vazio");
        }
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("Especie nao pode ser nula ou vazia");
        }
        if (tutor == null || tutor.trim().isEmpty()) {
            throw new IllegalArgumentException("Tutor nao pode ser nulo ou vazio");
        }

        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    // calcula a idade com base na data de nascimento
    public int calcularIdadeEmAnos() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTutor() { return tutor; }

    @Override
    public String toString() {
        return nome + " (" + especie + ")";
    }
}
