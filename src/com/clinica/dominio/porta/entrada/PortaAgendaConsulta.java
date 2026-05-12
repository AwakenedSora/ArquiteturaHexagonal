package com.clinica.dominio.porta.entrada;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.TipoConsulta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PortaAgendaConsulta {

    // caso de uso 1: agendar uma consulta
    Consulta agendarConsulta(Long animalId, Long veterinarioId,
                             LocalDate data, LocalTime hora,
                             TipoConsulta tipo);

    // caso de uso 2: registrar a realizacao de uma consulta
    Consulta realizarConsulta(Long consultaId, String observacoes);

    // caso de uso 3: cancelar uma consulta agendada
    void cancelarConsulta(Long consultaId);

    // caso de uso 4: historico de um animal
    List<Consulta> obterHistoricoAnimal(Long animalId);

    // caso de uso 5: agenda de um veterinario
    List<Consulta> obterAgendaVeterinario(Long vetId);
}
