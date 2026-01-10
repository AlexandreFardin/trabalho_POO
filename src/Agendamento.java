/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Agendamento {
    private static List<Agendamento> agendamentos = new ArrayList<>();

    private Date dataHoraInicio;
    private Date dataHoraFim;
    private String status;
    private Cliente cliente;
    private Profissional profissional;
    private ServicoProfissional servicoProfissional;
    private List<Notificacao> notificacoes = new ArrayList<>();


    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }
    public void setDataHoraInicio(Date dataHoraInicio) throws Exception {
        if (dataHoraInicio == null) {
            throw new Exception("Data de início não pode ser nula.");
        }
        this.dataHoraInicio = dataHoraInicio;
    }
    public Date getDataHoraFim() {
        return dataHoraFim;
    }
    public void setDataHoraFim(Date dataHoraFim) throws Exception {
        if (dataHoraFim == null) {
            throw new Exception("Data de fim não pode ser nula.");
        }
        this.dataHoraFim = dataHoraFim;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) throws Exception {
        if (cliente == null) {
            throw new Exception("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;
    }
    public Profissional getProfissional() {
        return profissional;
    }
    public void setProfissional(Profissional profissional) throws Exception {
        if (profissional == null) {
            throw new Exception("Profissional não pode ser nulo.");
        }
        this.profissional = profissional;
    }
    public ServicoProfissional getServicoProfissional() {
        return servicoProfissional;
    }
    public void setServicoProfissional(ServicoProfissional servicoProfissional) {
        this.servicoProfissional = servicoProfissional;
    }
    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }


    public Agendamento(Date dataHoraInicio, Date dataHoraFim, Cliente cliente, Profissional profissional, 
                       ServicoProfissional servicoProfissional) throws Exception {
        if (Administrador.isFeriado(dataHoraInicio)) {
            throw new Exception("Não é possível agendar em feriados.");
        }
        if (dataHoraInicio.after(dataHoraFim)) {
            throw new Exception("Data de início deve ser anterior à data de fim.");
        }
        setDataHoraInicio(dataHoraInicio);
        setDataHoraFim(dataHoraFim);
        setCliente(cliente);
        setProfissional(profissional);
        setServicoProfissional(servicoProfissional);
        setStatus("PENDENTE");
        agendamentos.add(this);
    }


    public void confirmar() {
        this.status = "CONFIRMADO";
        enviarNotificacao("CONFIRMACAO");
    }

    public void cancelar() throws Exception {
        PoliticaCancelamento politica = profissional.getPoliticaCancelamento();
        if (politica != null) {
            double taxa = politica.calcularTaxa(this);
            if (taxa > 0) {
                System.out.println("Taxa de cancelamento aplicada: R$ " + taxa);
            }
        }
        this.status = "CANCELADO";
        enviarNotificacao("CANCELAMENTO");
    }

    public void recusar() {
        this.status = "RECUSADO";
        enviarNotificacao("RECUSA");
    }

    public void remarcar(Date novaDataInicio, Date novaDataFim) throws Exception {
        if (Administrador.isFeriado(novaDataInicio)) {
            throw new Exception("Não é possível remarcar para feriados.");
        }
        this.dataHoraInicio = novaDataInicio;
        this.dataHoraFim = novaDataFim;
        enviarNotificacao("REMARCACAO");
    }

    public void enviarNotificacao(String tipo) {
        String mensagem = gerarMensagem(tipo);
        Notificacao notificacao = new Notificacao(tipo, mensagem, new Date(), this);
        notificacoes.add(notificacao);
        notificacao.enviar();
    }

    private String gerarMensagem(String tipo) {
        String nomeCliente = cliente.getNome();
        String nomeProfissional = profissional.getNome();
        
        switch (tipo) {
            case "CONFIRMACAO":
                return "Ola " + nomeCliente + ", seu agendamento com " + nomeProfissional + " foi confirmado.";
            case "CANCELAMENTO":
                return "Ola " + nomeCliente + ", seu agendamento com " + nomeProfissional + " foi cancelado.";
            case "REMARCACAO":
                return "Ola " + nomeCliente + ", seu agendamento com " + nomeProfissional + " foi remarcado.";
            case "RECUSA":
                return "Ola " + nomeCliente + ", seu agendamento com " + nomeProfissional + " foi recusado.";
            case "LEMBRETE":
                return "Ola " + nomeCliente + ", lembrete do seu agendamento com " + nomeProfissional + ".";
            default:
                return "Notificação sobre seu agendamento.";
        }
    }

    public static List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "dataHoraInicio=" + dataHoraInicio +
                ", dataHoraFim=" + dataHoraFim +
                ", status='" + status + '\'' +
                ", cliente=" + cliente.getNome() +
                ", profissional=" + profissional.getNome() +
                '}';
    }
}
