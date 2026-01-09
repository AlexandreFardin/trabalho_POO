public abstract class Pessoa {
    private String nome;
    private String email;
    private String telefone;
    private String senha;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new Exception("Nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) throws Exception {
        if (email == null || !email.contains("@")) {
            throw new Exception("Email inválido.");
        }
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) throws Exception {
        if (senha == null || senha.length() < 6) {
            throw new Exception("Senha deve ter pelo menos 6 caracteres.");
        }
        this.senha = senha;
    }


    public Pessoa(String nome, String email, String telefone, String senha) throws Exception {
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
        setSenha(senha);
    }


    public boolean login(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    public void logout() {
        System.out.println("Usuario " + nome + " deslogado.");
    }

    public void atualizarCadastro(String nome, String email, String telefone) throws Exception {
        if (nome != null && !nome.isEmpty()) {
            setNome(nome);
        }
        if (email != null) {
            setEmail(email);
        }
        if (telefone != null) {
            setTelefone(telefone);
        }
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
