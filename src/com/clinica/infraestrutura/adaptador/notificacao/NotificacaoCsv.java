package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;

public class NotificacaoCsv implements PortaNotificacaoTutor {

    private final String caminhoArquivo;

    public NotificacaoCsv(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        criarCabecalhoSeNecessario();
    }

    private void criarCabecalhoSeNecessario() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            try (FileWriter fw = new FileWriter(arquivo, true)) {
                fw.write("timestamp,tipo_evento,tutor,animal,veterinario,data_consulta\n");
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo CSV: " + e.getMessage());
            }
        }
    }

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        String linha = LocalDateTime.now() + ","
                + "AGENDAMENTO,"
                + tutor + ","
                + animal.getNome() + ","
                + consulta.getVeterinario().getNome() + ","
                + consulta.getData() + "T" + consulta.getHora();

        escreverLinha(linha);
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        String linha = LocalDateTime.now() + ","
                + "CANCELAMENTO,"
                + tutor + ","
                + animal.getNome() + ","
                + "-,"
                + "-";

        escreverLinha(linha);
    }

    private void escreverLinha(String linha) {
        try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
            fw.write(linha + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no CSV: " + e.getMessage());
        }
    }
}
