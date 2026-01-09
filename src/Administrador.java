import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Administrador extends Pessoa {
    private static List<Date> feriados = new ArrayList<>();


    public Administrador(String nome, String email, String telefone, String senha) throws Exception {
        super(nome, email, telefone, senha);
    }


    public void gerenciarServico(Servico servico, String acao) throws Exception {
        if (servico == null) {
            throw new Exception("Serviço não pode ser nulo.");
        }
        switch (acao.toUpperCase()) {
            case "ADICIONAR":
                Servico.adicionarServico(servico);
                break;
            case "REMOVER":
                Servico.removerServico(servico);
                break;
            default:
                throw new Exception("Ação inválida. Use ADICIONAR ou REMOVER.");
        }
    }

    public void cadastrarProfissional(Profissional prof) throws Exception {
        if (prof == null) {
            throw new Exception("Profissional não pode ser nulo.");
        }
        Profissional.cadastrarProfissional(prof);
    }

    public void gerenciarPolitica(Profissional prof, PoliticaCancelamento politica) throws Exception {
        if (prof == null || politica == null) {
            throw new Exception("Profissional e política não podem ser nulos.");
        }
        prof.setPoliticaCancelamento(politica);
    }

    public void adicionarFeriado(Date data) throws Exception {
        if (data == null) {
            throw new Exception("Data do feriado não pode ser nula.");
        }
        if (!feriados.contains(data)) {
            feriados.add(data);
        }
    }

    public void removerFeriado(Date data) {
        feriados.remove(data);
    }

    public static List<Date> getFeriados() {
        return feriados;
    }

    public static boolean isFeriado(Date data) {
        for (Date feriado : feriados) {
            if (mesmaData(feriado, data)) {
                return true;
            }
        }
        return false;
    }

    private static boolean mesmaData(Date d1, Date d2) {
        return d1.getYear() == d2.getYear() && 
               d1.getMonth() == d2.getMonth() && 
               d1.getDate() == d2.getDate();
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
