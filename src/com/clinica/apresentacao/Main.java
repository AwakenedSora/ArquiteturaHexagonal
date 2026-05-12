package com.clinica.apresentacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoConsole;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoCsv;
import com.clinica.infraestrutura.adaptador.persistencia.AnimalRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.ConsultaRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.VeterinarioRepositorioMemoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // repositorios compartilhados entre os dois servicos
        PortaAnimalRepositorio animais = new AnimalRepositorioMemoria();
        PortaVeterinarioRepositorio vets = new VeterinarioRepositorioMemoria();
        PortaConsultaRepositorio consultas = new ConsultaRepositorioMemoria();

        // passo 1: cadastrar os animais direto no repositorio
        Animal thor = new Animal(null, "Thor", "Cachorro", "Labrador",
                LocalDate.of(2020, 3, 10), "Joao Silva");
        animais.salvar(thor);

        Animal luna = new Animal(null, "Luna", "Gato", "Persa",
                LocalDate.of(2021, 6, 15), "Maria Souza");
        animais.salvar(luna);

        // passo 2: cadastrar os veterinarios
        Veterinario beatriz = new Veterinario(null, "Dra. Beatriz", "CRMV-GO-1234", "Clinica Geral");
        vets.salvar(beatriz);

        Veterinario carlos = new Veterinario(null, "Dr. Carlos", "CRMV-GO-5678", "Ortopedia");
        vets.salvar(carlos);

        System.out.println("=== Animais e veterinarios cadastrados ===");
        System.out.println("Animals: " + thor.getNome() + " (id=" + thor.getId() + "), "
                + luna.getNome() + " (id=" + luna.getId() + ")");
        System.out.println("Vets: " + beatriz.getNome() + " (id=" + beatriz.getId() + "), "
                + carlos.getNome() + " (id=" + carlos.getId() + ")");
        System.out.println();

        // passo 3: agendar consulta de ROTINA usando NotificacaoConsole
        PortaNotificacaoTutor notifConsole = new NotificacaoConsole();
        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(animais, vets, consultas, notifConsole);

        Consulta consulta1 = agenda.agendarConsulta(
                thor.getId(), beatriz.getId(),
                LocalDate.of(2025, 7, 15), LocalTime.of(14, 30),
                TipoConsulta.ROTINA);

        // passo 4: agendar consulta de EMERGENCIA usando NotificacaoCsv
        // troca o adaptador de notificacao sem alterar nada no dominio
        PortaNotificacaoTutor notifCsv = new NotificacaoCsv("notificacoes.csv");
        PortaAgendaConsulta agendaCsv = new ServicoAgendaConsulta(animais, vets, consultas, notifCsv);

        Consulta consulta2 = agendaCsv.agendarConsulta(
                luna.getId(), carlos.getId(),
                LocalDate.of(2025, 7, 16), LocalTime.of(9, 0),
                TipoConsulta.EMERGENCIA);

        System.out.println();

        // passo 5: realizar a primeira consulta
        agenda.realizarConsulta(consulta1.getId(), "Exame de rotina sem alteracoes.");
        System.out.println("[CONSULTA REALIZADA] Animal: " + consulta1.getAnimal().getNome()
                + " | Obs.: " + consulta1.getObservacoes());

        System.out.println();

        // passo 6: cancelar a segunda consulta e verificar liberacao do vet
        agendaCsv.cancelarConsulta(consulta2.getId());
        System.out.println("Situacao do Dr. Carlos apos cancelamento: "
                + carlos.getSituacao());

        System.out.println();

        // passo 7: historico de consultas do Thor
        List<Consulta> historico = agenda.obterHistoricoAnimal(thor.getId());
        System.out.println("=== Historico de " + thor.getNome() + " ===");
        for (Consulta c : historico) {
            System.out.printf(" Consulta #%d — %s %s | %s | %s%n",
                    c.getId(), c.getData(), c.getHora(), c.getTipo(), c.getSituacao());
        }

        System.out.println();

        // passo 8: agenda da Dra. Beatriz
        List<Consulta> agendaBeatriz = agenda.obterAgendaVeterinario(beatriz.getId());
        System.out.println("=== Agenda de " + beatriz.getNome() + " ===");
        for (Consulta c : agendaBeatriz) {
            System.out.printf(" Consulta #%d — %s %s | %s | %s%n",
                    c.getId(), c.getData(), c.getHora(), c.getTipo(), c.getSituacao());
        }
    }
}
