/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Pessoa {

    private static List<LocalDate> feriados = new ArrayList<>();
     private static List<Administrador> administradores = new ArrayList<>();
    
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

    public void adicionarFeriado(LocalDate data) throws Exception {
        if (data == null) {
            throw new Exception("Data do feriado não pode ser nula.");
        }
        if (!feriados.contains(data)) {
            feriados.add(data);
        }
    }

    public void removerFeriado(LocalDate data) {
        feriados.remove(data);
    }

    public static List<LocalDate> getFeriados() {
        return feriados;
    }

    public static boolean isFeriado(LocalDate data) {
        return feriados.contains(data);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

     public static List<Administrador> getAdministradores() {
        return administradores;
    }

     public void cadastrarAdministrador() {
        if (!administradores.contains(this)) {
            administradores.add(this);
        }
    }
}
