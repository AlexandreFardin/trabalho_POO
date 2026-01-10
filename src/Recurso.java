/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Recurso {

    private String nome;
    private String descricao;
    private List<LocalDateTime> horariosOcupados = new ArrayList<>();


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new Exception("Nome do recurso não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Recurso(String nome, String descricao) throws Exception {
        setNome(nome);
        setDescricao(descricao);
    }


    public boolean isDisponivel(LocalDateTime dataHora) {
        for (LocalDateTime ocupado : horariosOcupados) {
            if (mesmoHorario(ocupado, dataHora)) {
                return false;
            }
        }
        return true;
    }

    public void reservar(LocalDateTime dataHora) {
        if (!horariosOcupados.contains(dataHora)) {
            horariosOcupados.add(dataHora);
        }
    }

    public void liberar(LocalDateTime dataHora) {
        horariosOcupados.remove(dataHora);
    }

    /**
     * Verifica se dois horários coincidem na mesma hora (ano, mês, dia, hora)
     */
    private boolean mesmoHorario(LocalDateTime d1, LocalDateTime d2) {
        return d1.getYear() == d2.getYear() &&
               d1.getMonth() == d2.getMonth() &&
               d1.getDayOfMonth() == d2.getDayOfMonth() &&
               d1.getHour() == d2.getHour();
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
