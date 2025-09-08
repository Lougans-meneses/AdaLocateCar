package src.models;

public abstract class Cliente {
    private String nome;
    private String documento; // CPF ou CNPJ

    public Cliente(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
    }

    public String getNome() { return nome; }
    public String getDocumento() { return documento; }

    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "Nome: " + nome + " | Documento: " + documento;
    }
}
