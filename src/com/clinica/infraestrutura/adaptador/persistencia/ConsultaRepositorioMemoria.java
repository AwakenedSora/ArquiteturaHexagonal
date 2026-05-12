package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoConsulta;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConsultaRepositorioMemoria implements PortaConsultaRepositorio {

    private final Map<Long, Consulta> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Consulta consulta) {
        if (consulta.getId() == null) {
            consulta.setId(proximoId++);
        }
        store.put(consulta.getId(), consulta);
    }

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Consulta> buscarPorAnimal(Long animalId) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : store.values()) {
            if (c.getAnimal().getId().equals(animalId)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    @Override
    public List<Consulta> buscarPorVeterinario(Long vetId) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : store.values()) {
            if (c.getVeterinario().getId().equals(vetId)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    @Override
    public List<Consulta> listarAgendadas() {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : store.values()) {
            if (c.getSituacao() == SituacaoConsulta.AGENDADA) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}
