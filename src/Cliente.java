/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {

    private static List<Cliente> clientes = new ArrayList<>();

    private String cpf;
    private List<Agendamento> agendamentos = new ArrayList<>();
    private List<Busca> buscas = new ArrayList<>();


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws Exception {
        if (cpf == null || cpf.length() != 11) {
            throw new Exception("CPF inválido. Deve conter 11 dígitos.");
        }
        this.cpf = cpf;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public List<Busca> getBuscas() {
        return buscas;
    }


    public Cliente(String nome, String email, String telefone, String senha, String cpf) throws Exception {
        super(nome, email, telefone, senha);
        setCpf(cpf);
    }


    public void cadastrarCliente() {
        if (!clientes.contains(this)) {
            clientes.add(this);
        }
    }

    public List<ServicoProfissional> buscarServico(String filtro) {
        Busca busca = new Busca(
                filtro,
                LocalDateTime.now(),
                this
        );
        buscas.add(busca);
        return busca.executarBusca();
    }

    public boolean agendarHorario(Agendamento agendamento) throws Exception {
        if (agendamento == null) {
            throw new Exception("Agendamento não pode ser nulo.");
        }

        Profissional prof = agendamento.getProfissional();

        if (!prof.verificarDisponibilidade(
                agendamento.getDataHoraInicio(),
                agendamento.getDataHoraFim()
        )) {
            throw new Exception("Horário não disponível para este profissional.");
        }

        agendamentos.add(agendamento);
        prof.adicionarAgendamento(agendamento);
        return true;
    }

    public void cancelarHorario(Agendamento agendamento) throws Exception {
        if (agendamento == null || !agendamentos.contains(agendamento)) {
            throw new Exception("Agendamento não encontrado.");
        }
        agendamento.cancelar();
    }

    public void remarcarHorario(
            Agendamento agendamento,
            LocalDateTime novaDataInicio,
            LocalDateTime novaDataFim
    ) throws Exception {

        if (agendamento == null || !agendamentos.contains(agendamento)) {
            throw new Exception("Agendamento não encontrado.");
        }

        Profissional prof = agendamento.getProfissional();

        if (!prof.verificarDisponibilidade(novaDataInicio, novaDataFim)) {
            throw new Exception("Novo horário não disponível.");
        }

        agendamento.remarcar(novaDataInicio, novaDataFim);
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + getNome() + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

    
    
}

