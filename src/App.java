import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class App {

    static Scanner sc = new Scanner(System.in);
    

    // ================= UTIL =================

    static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    // ================= CADASTROS =================

    static void cadastrarCliente() throws Exception {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String tel = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        Cliente c = new Cliente(nome, email, tel, senha, cpf);
        c.cadastrarCliente();

        System.out.println("Cliente cadastrado.");
    }

    static void cadastrarAdministrador() throws Exception {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String tel = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Administrador a = new Administrador(nome, email, tel, senha);
        a.cadastrarAdministrador();

        System.out.println("Administrador cadastrado.");
    }

    static void cadastrarProfissional() throws Exception {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String tel = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = sc.nextLine();

        Profissional p = new Profissional(nome, email, tel, senha, cnpj);
        Profissional.cadastrarProfissional(p);


        System.out.println("Profissional cadastrado.");
    }

    // ================= LOGIN =================

    static Cliente loginCliente() {
    System.out.print("CPF: ");
    String cpf = sc.nextLine();
    System.out.print("Senha: ");
    String senha = sc.nextLine();

    for (Cliente c : Cliente.getClientes()) {
        if (c.getCpf().equals(cpf) && c.login(c.getEmail(), senha)) {
            return c;
        }
    }
    return null;
}


    static Profissional loginProfissional() {
        System.out.print("CNPJ: ");
        String cnpj = sc.nextLine();

        for (Profissional p : Profissional.getProfissionais()) {
            if (p.getCnpj().equals(cnpj)) return p;
        }
        return null;
    }

    static Administrador loginAdministrador() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for (Administrador a : Administrador.getAdministradores()) {
            if (a.login(email, senha)) return a;
        }
        return null;
    }

    // ================= AGENDAMENTO (SEM MÉTODOS FANTASMAS) =================

    static void criarAgendamento(Cliente cliente) throws Exception {

        List<Profissional> profs = Profissional.getProfissionais();
        if (profs.isEmpty()) {
            System.out.println("Nenhum profissional.");
            return;
        }

        for (int i = 0; i < profs.size(); i++)
            System.out.println(i + " - " + profs.get(i).getNome());

        int p = lerInt();
        Profissional prof = profs.get(p);

        List<ServicoProfissional> sps = prof.getServicosPrestados();
        if (sps.isEmpty()) {
            System.out.println("Sem serviços.");
            return;
        }

        for (int i = 0; i < sps.size(); i++)
            System.out.println(i + " - " + sps.get(i));

        int s = lerInt();
        ServicoProfissional sp = sps.get(s);

        System.out.print("Inicio (AAAA-MM-DDTHH:MM): ");
        LocalDateTime ini = LocalDateTime.parse(sc.nextLine());
        LocalDateTime fim = ini.plusMinutes(sp.getDuracaoMinutos());

        Agendamento ag = new Agendamento(ini, fim, cliente, prof, sp);
        cliente.agendarHorario(ag);

        System.out.println("Agendamento criado.");
    }

    static void cancelarAgendamento(Cliente cliente) {
        List<Agendamento> ags = cliente.getAgendamentos();

        if (ags.isEmpty()) {
            System.out.println("Nenhum agendamento.");
            return;
        }

        for (int i = 0; i < ags.size(); i++)
            System.out.println(i + " - " + ags.get(i));

        int op = lerInt();
        ags.remove(op);

        System.out.println("Agendamento removido.");
    }

    // ================= MENUS =================

    static void menuCliente(Cliente c) {
        int op;
        do {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1 - Criar Agendamento");
            System.out.println("2 - Meus Agendamentos");
            System.out.println("3 - Cancelar Agendamento");
            System.out.println("4 - Buscar Serviço");
            System.out.println("5 - Avaliar Profissional");
            System.out.println("0 - Voltar");

            op = lerInt();

            try {
                switch (op) {
                    case 1:
                        criarAgendamento(c);
                        break;
                    case 2:
                        c.getAgendamentos().forEach(System.out::println);
                        break;
                    case 3:
                        cancelarAgendamento(c);
                        break;
                    case 4:
                        System.out.print("Filtro: ");
                        String filtro = sc.nextLine();
                        List<ServicoProfissional> res = c.buscarServico(filtro);
                        res.forEach(System.out::println);
                        break;
                    case 5:
                        System.out.print("CNPJ do profissional: ");
                        String cnpj = sc.nextLine();

                        Profissional profAvaliado = null;
                        for (Profissional p : Profissional.getProfissionais()) {
                            if (p.getCnpj().equals(cnpj)) {
                                profAvaliado = p;
                                break;
                            }
                        }

                        if (profAvaliado == null) {
                            System.out.println("Profissional não encontrado.");
                            break;
                        }

                        System.out.print("Nota (1 a 5): ");
                        int nota = lerInt();
                        System.out.print("Comentário: ");
                        String comentario = sc.nextLine();

                        new Nota(nota, comentario, LocalDate.now(), c, profAvaliado);
                        System.out.println("Avaliação registrada.");
                        break;


                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (op != 0);
    }

    static void menuAdministrador(Administrador admin) {
    int op;
    do {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1 - Criar Serviço");
        System.out.println("2 - Remover Serviço");
        System.out.println("3 - Cadastrar Profissional");
        System.out.println("4 - Definir Política de Cancelamento");
        System.out.println("5 - Adicionar Feriado");
        System.out.println("0 - Voltar");

        op = lerInt();

        try {
            switch (op) {

                case 1: { // criar serviço
                    System.out.print("Nome do serviço: ");
                    String nome = sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();
                    System.out.print("Tempo mínimo entre atendimentos (min): ");
                    int tempo = lerInt();

                    Servico s = new Servico(nome, desc, tempo);
                    admin.gerenciarServico(s, "ADICIONAR");

                    System.out.println("Serviço criado.");
                    break;
                }

                case 2: { // remover serviço
                    List<Servico> servicos = Servico.getServicos();
                    for (int i = 0; i < servicos.size(); i++)
                        System.out.println(i + " - " + servicos.get(i));

                    int idx = lerInt();
                    admin.gerenciarServico(servicos.get(idx), "REMOVER");

                    System.out.println("Serviço removido.");
                    break;
                }

                case 3:
                    cadastrarProfissional();
                    break;

                case 4: { // política de cancelamento
                    List<Profissional> profs = Profissional.getProfissionais();
                    for (int i = 0; i < profs.size(); i++)
                        System.out.println(i + " - " + profs.get(i));

                    int idx = lerInt();
                    Profissional prof = profs.get(idx);

                    System.out.print("Nome da política: ");
                    String nome = sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();
                    System.out.print("Prazo mínimo (horas): ");
                    int prazo = lerInt();
                    System.out.print("Taxa no-show (%): ");
                    double taxa = Double.parseDouble(sc.nextLine());

                    PoliticaCancelamento pc =
                            new PoliticaCancelamento(nome, desc, prazo, taxa);

                    admin.gerenciarPolitica(prof, pc);

                    System.out.println("Política aplicada ao profissional.");
                    break;
                }

                case 5: { // feriado
                    System.out.print("Data (AAAA-MM-DD): ");
                    LocalDate d = LocalDate.parse(sc.nextLine());
                    admin.adicionarFeriado(d);
                    System.out.println("Feriado adicionado.");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    } while (op != 0);
}

    static void menuProfissional(Profissional p) {
    int op;
    do {
        System.out.println("\n=== MENU PROFISSIONAL ===");
        System.out.println("1 - Ver Agenda");
        System.out.println("2 - Aprovar Agendamento");
        System.out.println("3 - Recusar Agendamento");
        System.out.println("4 - Associar Serviço");
        System.out.println("0 - Voltar");

        op = lerInt();

        try {
            switch (op) {
                case 1:
                    p.getAgenda().forEach(System.out::println);
                    break;

                case 2:
                case 3:
                    List<Agendamento> ags = p.getAgenda();
                    for (int i = 0; i < ags.size(); i++)
                        System.out.println(i + " - " + ags.get(i));

                    int idx = lerInt();
                    if (op == 2)
                        p.aprovarAgendamento(ags.get(idx));
                    else
                        p.recusarAgendamento(ags.get(idx));
                    break;
                    case 4: {
                        List<Servico> servicos = Servico.getServicos();
                        for (int i = 0; i < servicos.size(); i++)
                            System.out.println(i + " - " + servicos.get(i));

                        int idz = lerInt();
                        Servico s = servicos.get(idz);

                        System.out.print("Preço: ");
                        double preco = Double.parseDouble(sc.nextLine());
                        System.out.print("Duração (min): ");
                        int dur = lerInt();

                        new ServicoProfissional(s, p, preco, dur);

                        System.out.println("Serviço associado ao profissional.");
                        break;
                    }

            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    } while (op != 0);
}

    // ================= MAIN =================

    public static void main(String[] args) {

        int op;

        do {
            System.out.println("\n=== SISTEMA ===");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Administrador");
            System.out.println("3 - Login Cliente");
            System.out.println("4 - Login Profissional");
            System.out.println("5 - Login Administrador");
            System.out.println("0 - Sair");

            op = lerInt();

            try {
                switch (op) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        cadastrarAdministrador();
                        break;
                    case 3: {
                        Cliente c = loginCliente();
                        if (c != null) menuCliente(c);
                        break;
                    }
                     case 4: {
                        Profissional p = loginProfissional();
                        if (p != null) menuProfissional(p);
                        break;
                    }
                    case 5: {
                        Administrador a = loginAdministrador();
                        if (a != null) menuAdministrador(a);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (op != 0);

        System.out.println("Encerrado.");
    }
}
