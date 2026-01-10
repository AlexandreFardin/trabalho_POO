/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.Duration;
import java.time.LocalDateTime;

public class PoliticaCancelamento {

    private String nomeRegra;
    private String descricao;
    private int prazoMinimoHoras;
    private double taxaNoShow;


    public String getNomeRegra() {
        return nomeRegra;
    }

    public void setNomeRegra(String nomeRegra) {
        this.nomeRegra = nomeRegra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrazoMinimoHoras() {
        return prazoMinimoHoras;
    }

    public void setPrazoMinimoHoras(int prazoMinimoHoras) throws Exception {
        if (prazoMinimoHoras < 0) {
            throw new Exception("Prazo mínimo não pode ser negativo.");
        }
        this.prazoMinimoHoras = prazoMinimoHoras;
    }

    public double getTaxaNoShow() {
        return taxaNoShow;
    }

    public void setTaxaNoShow(double taxaNoShow) throws Exception {
        if (taxaNoShow < 0 || taxaNoShow > 100) {
            throw new Exception("Taxa de no-show deve estar entre 0 e 100.");
        }
        this.taxaNoShow = taxaNoShow;
    }


    public PoliticaCancelamento(
            String nomeRegra,
            String descricao,
            int prazoMinimoHoras,
            double taxaNoShow
    ) throws Exception {
        setNomeRegra(nomeRegra);
        setDescricao(descricao);
        setPrazoMinimoHoras(prazoMinimoHoras);
        setTaxaNoShow(taxaNoShow);
    }


    public double calcularTaxa(Agendamento agendamento) {

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataAgendamento = agendamento.getDataHoraInicio();

        long diferencaHoras = Duration.between(agora, dataAgendamento).toHours();

        if (diferencaHoras < prazoMinimoHoras) {
            ServicoProfissional sp = agendamento.getServicoProfissional();
            if (sp != null) {
                return sp.getPreco() * (taxaNoShow / 100);
            }
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "PoliticaCancelamento{" +
                "nomeRegra='" + nomeRegra + '\'' +
                ", prazoMinimoHoras=" + prazoMinimoHoras +
                ", taxaNoShow=" + taxaNoShow + "%" +
                '}';
    }
}
