/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.LocalDateTime;

public class Notificacao {

    private String tipo;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private Agendamento agendamento;
    private boolean enviada;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public boolean isEnviada() {
        return enviada;
    }


    public Notificacao(
            String tipo,
            String mensagem,
            LocalDateTime dataEnvio,
            Agendamento agendamento
    ) {
        setTipo(tipo);
        setMensagem(mensagem);
        setDataEnvio(dataEnvio);
        setAgendamento(agendamento);
        this.enviada = false;
    }


    public boolean enviar() {
        System.out.println("[NOTIFICACAO - " + tipo + "] " + mensagem);
        this.enviada = true;
        return true;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "tipo='" + tipo + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", dataEnvio=" + dataEnvio +
                ", enviada=" + enviada +
                '}';
    }
}
