package src.models;

import java.time.LocalDateTime;

public class Aluguel {
    private String local;
    private LocalDateTime retirada;
    private LocalDateTime devolucao;
    private Veiculo veiculo;
    private Cliente cliente;

    public Aluguel(String local, LocalDateTime retirada, Veiculo veiculo, Cliente cliente) {
        this.local = local;
        this.retirada = retirada;
        this.veiculo = veiculo;
        this.cliente = cliente;
    }

    public String getLocal() {
        return local;
    }

    public LocalDateTime getRetirada() {
        return retirada;
    }

    public LocalDateTime getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(LocalDateTime devolucao) {
        this.devolucao = devolucao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
