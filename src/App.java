import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        List<Date> horariosJoao = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        horariosJoao.add(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 10);
        horariosJoao.add(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 14);
        horariosJoao.add(cal.getTime());
        joao.definirHorarios(horariosJoao);

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
        Calendar calInicio = Calendar.getInstance();
        calInicio.add(Calendar.DAY_OF_MONTH, 1);
        calInicio.set(Calendar.HOUR_OF_DAY, 10);
        calInicio.set(Calendar.MINUTE, 0);
        
        Calendar calFim = Calendar.getInstance();
        calFim.add(Calendar.DAY_OF_MONTH, 1);
        calFim.set(Calendar.HOUR_OF_DAY, 10);
        calFim.set(Calendar.MINUTE, 30);

        Agendamento agendamento1 = new Agendamento(
            calInicio.getTime(), 
            calFim.getTime(), 
            pedro, 
            joao, 
            spJoaoCorte
        );
        
        pedro.agendarHorario(agendamento1);
        System.out.println("Agendamento criado: " + agendamento1);

        joao.aprovarAgendamento(agendamento1);
        System.out.println("Status apos aprovacao: " + agendamento1.getStatus());

        System.out.println("\n--- Avaliacao ---");
        Nota avaliacao = new Nota(5, "Excelente profissional!", new Date(), pedro, joao);
        System.out.println("Avaliacao registrada: " + avaliacao);
        System.out.println("Media de avaliacoes do Joao: " + joao.getMediaAvaliacoes());

        System.out.println("\n--- Recomendacoes ---");
        Busca buscaAna = new Busca("corte", new Date(), ana);
        List<Profissional> recomendados = buscaAna.getRecomendacoes(corte);
        System.out.println("Profissionais recomendados para 'Corte de Cabelo':");
        for (Profissional p : recomendados) {
            System.out.println("  - " + p.getNome() + " (Media: " + p.getMediaAvaliacoes() + ")");
        }

        System.out.println("\n--- Remarcacao ---");
        Calendar novoInicio = Calendar.getInstance();
        novoInicio.add(Calendar.DAY_OF_MONTH, 2);
        novoInicio.set(Calendar.HOUR_OF_DAY, 14);
        novoInicio.set(Calendar.MINUTE, 0);
        
        Calendar novoFim = Calendar.getInstance();
        novoFim.add(Calendar.DAY_OF_MONTH, 2);
        novoFim.set(Calendar.HOUR_OF_DAY, 14);
        novoFim.set(Calendar.MINUTE, 30);
        
        pedro.remarcarHorario(agendamento1, novoInicio.getTime(), novoFim.getTime());
        System.out.println("Agendamento remarcado: " + agendamento1);

        System.out.println("\n--- Notificacoes do Agendamento ---");
        for (Notificacao n : agendamento1.getNotificacoes()) {
            System.out.println("  - " + n.getTipo() + ": " + n.getMensagem());
        }

        System.out.println("\n--- Gerenciando Feriados ---");
        Calendar feriado = Calendar.getInstance();
        feriado.set(Calendar.MONTH, Calendar.DECEMBER);
        feriado.set(Calendar.DAY_OF_MONTH, 25);
        admin.adicionarFeriado(feriado.getTime());
        System.out.println("Feriado adicionado: 25/12");

        System.out.println("\n=== Fim da Demonstracao ===");
    }
}
