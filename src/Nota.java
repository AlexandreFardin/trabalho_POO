import java.util.Date;

public class Nota {
    private int pontuacao;
    private String comentario;
    private Date data;
    private Cliente cliente;
    private Profissional profissional;


    public int getPontuacao() {
        return pontuacao;
    }
    public void setPontuacao(int pontuacao) throws Exception {
        if (pontuacao < 1 || pontuacao > 5) {
            throw new Exception("Pontuação deve estar entre 1 e 5.");
        }
        this.pontuacao = pontuacao;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Profissional getProfissional() {
        return profissional;
    }
    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }


    public Nota(int pontuacao, String comentario, Date data, Cliente cliente, Profissional profissional) throws Exception {
        setPontuacao(pontuacao);
        setComentario(comentario);
        setData(data);
        setCliente(cliente);
        setProfissional(profissional);
        
        if (profissional != null) {
            profissional.adicionarAvaliacao(this);
        }
    }

    @Override
    public String toString() {
        return "Nota{" +
                "pontuacao=" + pontuacao +
                ", comentario='" + comentario + '\'' +
                ", data=" + data +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") +
                ", profissional=" + (profissional != null ? profissional.getNome() : "N/A") +
                '}';
    }
}
