/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.util.ArrayList;
import java.util.List;

public class Servico {
    private static List<Servico> servicos = new ArrayList<>();

    private String nome;
    private String descricao;
    private int tempoMinimoEntreAtendimento;
    private List<Recurso> recursos = new ArrayList<>();


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new Exception("Nome do serviço não pode ser vazio.");
        }
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getTempoMinimoEntreAtendimento() {
        return tempoMinimoEntreAtendimento;
    }
    public void setTempoMinimoEntreAtendimento(int tempo) throws Exception {
        if (tempo < 0) {
            throw new Exception("Tempo mínimo não pode ser negativo.");
        }
        this.tempoMinimoEntreAtendimento = tempo;
    }
    public List<Recurso> getRecursos() {
        return recursos;
    }


    public Servico(String nome, String descricao, int tempoMinimoEntreAtendimento) throws Exception {
        setNome(nome);
        setDescricao(descricao);
        setTempoMinimoEntreAtendimento(tempoMinimoEntreAtendimento);
    }


    public void adicionarRecurso(Recurso recurso) {
        if (!recursos.contains(recurso)) {
            recursos.add(recurso);
        }
    }

    public void removerRecurso(Recurso recurso) {
        recursos.remove(recurso);
    }

    public static List<Servico> getServicos() {
        return servicos;
    }

    public static void adicionarServico(Servico servico) {
        if (!servicos.contains(servico)) {
            servicos.add(servico);
        }
    }

    public static void removerServico(Servico servico) {
        servicos.remove(servico);
    }

    @Override
    public String toString() {
        return "Servico{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tempoMinimoEntreAtendimento=" + tempoMinimoEntreAtendimento +
                '}';
    }
}
