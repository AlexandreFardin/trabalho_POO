/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Busca {

    private String filtro;
    private LocalDateTime dataBusca;
    private Cliente cliente;
    private List<ServicoProfissional> resultados = new ArrayList<>();


    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public LocalDateTime getDataBusca() {
        return dataBusca;
    }

    public void setDataBusca(LocalDateTime dataBusca) {
        this.dataBusca = dataBusca;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ServicoProfissional> getResultados() {
        return resultados;
    }


    public Busca(String filtro, LocalDateTime dataBusca, Cliente cliente) {
        setFiltro(filtro);
        setDataBusca(dataBusca);
        setCliente(cliente);
    }


    public List<ServicoProfissional> executarBusca() {
        resultados.clear();
        List<ServicoProfissional> todos = ServicoProfissional.getServicosProfissionais();
        
        for (ServicoProfissional sp : todos) {
            if (correspondeAoFiltro(sp)) {
                resultados.add(sp);
            }
        }
        return resultados;
    }

    private boolean correspondeAoFiltro(ServicoProfissional sp) {
        if (filtro == null || filtro.isEmpty()) {
            return true;
        }
        String filtroLower = filtro.toLowerCase();
        String nomeServico = sp.getServico().getNome().toLowerCase();
        String nomeProfissional = sp.getProfissional().getNome().toLowerCase();
        String descricao = sp.getServico().getDescricao().toLowerCase();
        
        return nomeServico.contains(filtroLower) || 
               nomeProfissional.contains(filtroLower) ||
               descricao.contains(filtroLower);
    }

    // Retorna profissionais ordenados por avaliação
    public List<Profissional> getRecomendacoes(Servico servico) {
        List<Profissional> recomendados = new ArrayList<>();
        List<ServicoProfissional> servicosProfissionais = ServicoProfissional.buscarPorServico(servico);
        
        for (ServicoProfissional sp : servicosProfissionais) {
            Profissional prof = sp.getProfissional();
            if (!recomendados.contains(prof)) {
                recomendados.add(prof);
            }
        }
        
        recomendados.sort(new Comparator<Profissional>() {
            @Override
            public int compare(Profissional p1, Profissional p2) {
                return Double.compare(p2.getMediaAvaliacoes(), p1.getMediaAvaliacoes());
            }
        });
        
        return recomendados;
    }

    public void avaliarProfissional(Profissional prof, Nota nota) {
        if (prof != null && nota != null) {
            prof.adicionarAvaliacao(nota);
        }
    }

    @Override
    public String toString() {
        return "Busca{" +
                "filtro='" + filtro + '\'' +
                ", dataBusca=" + dataBusca +
                ", resultados=" + resultados.size() +
                '}';
    }
}
