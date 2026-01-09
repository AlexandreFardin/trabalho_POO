import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recurso {
    private String nome;
    private String descricao;
    private List<Date> horariosOcupados = new ArrayList<>();


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


    public boolean isDisponivel(Date dataHora) {
        for (Date ocupado : horariosOcupados) {
            if (mesmoHorario(ocupado, dataHora)) {
                return false;
            }
        }
        return true;
    }

    public void reservar(Date dataHora) {
        if (!horariosOcupados.contains(dataHora)) {
            horariosOcupados.add(dataHora);
        }
    }

    public void liberar(Date dataHora) {
        horariosOcupados.remove(dataHora);
    }

    private boolean mesmoHorario(Date d1, Date d2) {
        return d1.getYear() == d2.getYear() && 
               d1.getMonth() == d2.getMonth() && 
               d1.getDate() == d2.getDate() &&
               d1.getHours() == d2.getHours();
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
