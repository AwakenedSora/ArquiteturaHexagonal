package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnimalRepositorioMemoria implements PortaAnimalRepositorio {

    private final Map<Long, Animal> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Animal animal) {
        if (animal.getId() == null) {
            animal.setId(proximoId++);
        }
        store.put(animal.getId(), animal);
    }

    @Override
    public Optional<Animal> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Animal> listarPorTutor(String tutor) {
        List<Animal> resultado = new ArrayList<>();
        for (Animal a : store.values()) {
            if (a.getTutor().equalsIgnoreCase(tutor)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    @Override
    public List<Animal> listarTodos() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void remover(Long id) {
        store.remove(id);
    }
}
