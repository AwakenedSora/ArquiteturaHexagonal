package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VeterinarioRepositorioMemoria implements PortaVeterinarioRepositorio {

    private final Map<Long, Veterinario> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Veterinario vet) {
        if (vet.getId() == null) {
            vet.setId(proximoId++);
        }
        store.put(vet.getId(), vet);
    }

    @Override
    public Optional<Veterinario> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Veterinario> buscarDisponiveis() {
        List<Veterinario> disponiveis = new ArrayList<>();
        for (Veterinario v : store.values()) {
            if (v.estaDisponivel()) {
                disponiveis.add(v);
            }
        }
        return disponiveis;
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {
        List<Veterinario> resultado = new ArrayList<>();
        for (Veterinario v : store.values()) {
            if (v.getEspecialidade().equalsIgnoreCase(especialidade)) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
