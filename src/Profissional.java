import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Profissional extends Pessoa {
    private static List<Profissional> profissionais = new ArrayList<>();
    
    private String cnpj;
    private List<Date> horariosDisponiveis = new ArrayList<>();
    private List<Agendamento> agendamentos = new ArrayList<>();
    private List<ServicoProfissional> servicosPrestados = new ArrayList<>();
    private PoliticaCancelamento politicaCancelamento;
    private List<Nota> avaliacoes = new ArrayList<>();


    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) throws Exception {
        if (cnpj == null || cnpj.length() != 14) {
            throw new Exception("CNPJ inválido. Deve conter 14 dígitos.");
        }
        this.cnpj = cnpj;
    }
    public List<Date> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }
    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }
    public List<ServicoProfissional> getServicosPrestados() {
        return servicosPrestados;
    }
    public PoliticaCancelamento getPoliticaCancelamento() {
        return politicaCancelamento;
    }
    public void setPoliticaCancelamento(PoliticaCancelamento politica) {
        this.politicaCancelamento = politica;
    }
    public List<Nota> getAvaliacoes() {
        return avaliacoes;
    }


    public Profissional(String nome, String email, String telefone, String senha, String cnpj) throws Exception {
        super(nome, email, telefone, senha);
        setCnpj(cnpj);
    }


    public void definirHorarios(List<Date> horarios) {
        this.horariosDisponiveis = new ArrayList<>(horarios);
    }

    public void adicionarHorarioDisponivel(Date horario) {
        if (!horariosDisponiveis.contains(horario)) {
            horariosDisponiveis.add(horario);
        }
    }

    public void adicionarServico(ServicoProfissional servico) {
        if (!servicosPrestados.contains(servico)) {
            servicosPrestados.add(servico);
        }
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        if (!agendamentos.contains(agendamento)) {
            agendamentos.add(agendamento);
        }
    }

    public boolean verificarDisponibilidade(Date inicio, Date fim) {
        for (Agendamento ag : agendamentos) {
            if (ag.getStatus().equals("CONFIRMADO") || ag.getStatus().equals("PENDENTE")) {
                if (inicio.before(ag.getDataHoraFim()) && fim.after(ag.getDataHoraInicio())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void aprovarAgendamento(Agendamento agendamento) throws Exception {
        if (agendamento == null || !agendamentos.contains(agendamento)) {
            throw new Exception("Agendamento não encontrado na agenda do profissional.");
        }
        agendamento.confirmar();
    }

    public void recusarAgendamento(Agendamento agendamento) throws Exception {
        if (agendamento == null || !agendamentos.contains(agendamento)) {
            throw new Exception("Agendamento não encontrado na agenda do profissional.");
        }
        agendamento.recusar();
    }

    public void adicionarAvaliacao(Nota nota) {
        avaliacoes.add(nota);
    }

    public double getMediaAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        double soma = 0;
        for (Nota n : avaliacoes) {
            soma += n.getPontuacao();
        }
        return soma / avaliacoes.size();
    }

    public List<Agendamento> getAgenda() {
        return new ArrayList<>(agendamentos);
    }

    public static List<Profissional> getProfissionais() {
        return profissionais;
    }

    public static void cadastrarProfissional(Profissional prof) {
        if (!profissionais.contains(prof)) {
            profissionais.add(prof);
        }
    }

    @Override
    public String toString() {
        return "Profissional{" +
                "nome='" + getNome() + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + getEmail() + '\'' +
                ", mediaAvaliacoes=" + getMediaAvaliacoes() +
                '}';
    }
}
