/*
    @Author: Alexandre Fardin, Lilanio Costa e Alceu Felix
*/

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Sistema de Agendamentos ===\n");

        Administrador admin = new Administrador("Carlos Admin", "admin@empresa.com", "27999999999", "admin123");

        PoliticaCancelamento politicaPadrao = new PoliticaCancelamento(
            "Politica Padrao", 
            "Cancelamento com ate 24h de antecedencia", 
            24, 
            50.0
        );

        Servico corte = new Servico("Corte de Cabelo", "Corte masculino tradicional", 15);
        Servico barba = new Servico("Barba", "Aparar e modelar barba", 10);
        Servico completo = new Servico("Corte + Barba", "Pacote completo", 20);

        admin.gerenciarServico(corte, "ADICIONAR");
        admin.gerenciarServico(barba, "ADICIONAR");
        admin.gerenciarServico(completo, "ADICIONAR");

        Recurso sala1 = new Recurso("Sala 1", "Sala principal com espelho grande");
        Recurso sala2 = new Recurso("Sala 2", "Sala secundaria");

        corte.adicionarRecurso(sala1);
        barba.adicionarRecurso(sala1);
        completo.adicionarRecurso(sala2);

        Profissional joao = new Profissional("Joao Barbeiro", "joao@barbearia.com", "27988888888", "joao123", "12345678901234");
        Profissional maria = new Profissional("Maria Cabeleireira", "maria@barbearia.com", "27977777777", "maria123", "98765432109876");

        admin.cadastrarProfissional(joao);
        admin.cadastrarProfissional(maria);

        admin.gerenciarPolitica(joao, politicaPadrao);
        admin.gerenciarPolitica(maria, politicaPadrao);

        // Horários de João
        List<LocalDateTime> horariosJoao = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        horariosJoao.add(LocalDateTime.of(hoje, LocalTime.of(9, 0)));
        horariosJoao.add(LocalDateTime.of(hoje, LocalTime.of(10, 0)));
        horariosJoao.add(LocalDateTime.of(hoje, LocalTime.of(14, 0)));
        joao.definirHorarios(horariosJoao);

        // Serviços prestados
        ServicoProfissional spJoaoCorte = new ServicoProfissional(corte, joao, 35.0, 30);
        ServicoProfissional spJoaoBarba = new ServicoProfissional(barba, joao, 25.0, 20);
        ServicoProfissional spMariaCorte = new ServicoProfissional(corte, maria, 40.0, 30);
        ServicoProfissional spMariaCompleto = new ServicoProfissional(completo, maria, 60.0, 50);

        System.out.println("Servicos cadastrados: " + Servico.getServicos().size());
        System.out.println("Profissionais cadastrados: " + Profissional.getProfissionais().size());

        Cliente pedro = new Cliente("Pedro Silva", "pedro@email.com", "27966666666", "pedro123", "11122233344");
        Cliente ana = new Cliente("Ana Santos", "ana@email.com", "27955555555", "ana12345", "55566677788");

        pedro.cadastrarCliente();
        ana.cadastrarCliente();

        System.out.println("Clientes cadastrados: " + Cliente.getClientes().size());

        System.out.println("\n--- Busca de Servicos ---");
        List<ServicoProfissional> resultados = pedro.buscarServico("corte");
        System.out.println("Resultados para 'corte': " + resultados.size());
        for (ServicoProfissional sp : resultados) {
            System.out.println("  - " + sp);
        }

        System.out.println("\n--- Criando Agendamento ---");
        LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        LocalDateTime fim = inicio.plusMinutes(30);

        Agendamento agendamento1 = new Agendamento(inicio, fim, pedro, joao, spJoaoCorte);

        pedro.agendarHorario(agendamento1);
        System.out.println("Agendamento criado: " + agendamento1);

        joao.aprovarAgendamento(agendamento1);
        System.out.println("Status apos aprovacao: " + agendamento1.getStatus());

        System.out.println("\n--- Avaliacao ---");
        Nota avaliacao = new Nota(5, "Excelente profissional!", LocalDate.now(), pedro, joao);
        System.out.println("Avaliacao registrada: " + avaliacao);
        System.out.println("Media de avaliacoes do Joao: " + joao.getMediaAvaliacoes());

        System.out.println("\n--- Recomendacoes ---");
        Busca buscaAna = new Busca("corte", LocalDateTime.now(), ana);
        List<Profissional> recomendados = buscaAna.getRecomendacoes(corte);
        System.out.println("Profissionais recomendados para 'Corte de Cabelo':");
        for (Profissional p : recomendados) {
            System.out.println("  - " + p.getNome() + " (Media: " + p.getMediaAvaliacoes() + ")");
        }

        System.out.println("\n--- Remarcacao ---");
        LocalDateTime novoInicio = LocalDateTime.now().plusDays(2).withHour(14).withMinute(0);
        LocalDateTime novoFim = novoInicio.plusMinutes(30);

        pedro.remarcarHorario(agendamento1, novoInicio, novoFim);
        System.out.println("Agendamento remarcado: " + agendamento1);

        System.out.println("\n--- Notificacoes do Agendamento ---");
        for (Notificacao n : agendamento1.getNotificacoes()) {
            System.out.println("  - " + n.getTipo() + ": " + n.getMensagem());
        }

        System.out.println("\n--- Gerenciando Feriados ---");
        LocalDate feriado = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 25);
        admin.adicionarFeriado(feriado);
        System.out.println("Feriado adicionado: 25/12");

        System.out.println("\n=== Fim da Demonstracao ===");
    }
}
