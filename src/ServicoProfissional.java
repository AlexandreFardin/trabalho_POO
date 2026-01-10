/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.util.ArrayList;
import java.util.List;

public class ServicoProfissional {
    private static List<ServicoProfissional> servicosProfissionais = new ArrayList<>();

    private Servico servico;
    private Profissional profissional;
    private double preco;
    private int duracaoMinutos;


    public Servico getServico() {
        return servico;
    }
    public void setServico(Servico servico) throws Exception {
        if (servico == null) {
            throw new Exception("Serviço não pode ser nulo.");
        }
        this.servico = servico;
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
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) throws Exception {
        if (preco < 0) {
            throw new Exception("Preço não pode ser negativo.");
        }
        this.preco = preco;
    }
    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }
    public void setDuracaoMinutos(int duracaoMinutos) throws Exception {
        if (duracaoMinutos <= 0) {
            throw new Exception("Duração deve ser maior que zero.");
        }
        this.duracaoMinutos = duracaoMinutos;
    }


    public ServicoProfissional(Servico servico, Profissional profissional, double preco, int duracaoMinutos) throws Exception {
        setServico(servico);
        setProfissional(profissional);
        setPreco(preco);
        setDuracaoMinutos(duracaoMinutos);
        profissional.adicionarServico(this);
        servicosProfissionais.add(this);
    }


    public static List<ServicoProfissional> getServicosProfissionais() {
        return servicosProfissionais;
    }

    public static List<ServicoProfissional> buscarPorServico(Servico servico) {
        List<ServicoProfissional> resultado = new ArrayList<>();
        for (ServicoProfissional sp : servicosProfissionais) {
            if (sp.getServico().equals(servico)) {
                resultado.add(sp);
            }
        }
        return resultado;
    }

    public static List<ServicoProfissional> buscarPorProfissional(Profissional profissional) {
        List<ServicoProfissional> resultado = new ArrayList<>();
        for (ServicoProfissional sp : servicosProfissionais) {
            if (sp.getProfissional().equals(profissional)) {
                resultado.add(sp);
            }
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "ServicoProfissional{" +
                "servico=" + servico.getNome() +
                ", profissional=" + profissional.getNome() +
                ", preco=" + preco +
                ", duracaoMinutos=" + duracaoMinutos +
                '}';
    }
}
