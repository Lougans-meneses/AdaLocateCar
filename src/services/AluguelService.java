package src.services;

import src.models.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class AluguelService {
    private List<Aluguel> alugueis = new ArrayList<>();

    public boolean alugarVeiculo(String local, LocalDateTime retirada, Veiculo veiculo, Cliente cliente) {
        if (!veiculo.isDisponivel()) return false;
        veiculo.setDisponivel(false);
        Aluguel aluguel = new Aluguel(local, retirada, veiculo, cliente);
        alugueis.add(aluguel);
        return true;
    }

    public double devolverVeiculo(Aluguel aluguel, LocalDateTime devolucao) {
        aluguel.setDevolucao(devolucao);
        Veiculo veiculo = aluguel.getVeiculo();
        veiculo.setDisponivel(true);
        long dias = calcularDiarias(aluguel.getRetirada(), devolucao);
        double valorDiaria = veiculo.getValorDiaria();
        double total = dias * valorDiaria;
        double desconto = calcularDesconto(aluguel.getCliente(), dias, total);
        return total - desconto;
    }

    private long calcularDiarias(LocalDateTime retirada, LocalDateTime devolucao) {
        long horas = ChronoUnit.HOURS.between(retirada, devolucao);
        long dias = horas / 24;
        if (horas % 24 != 0) dias++;
        return dias;
    }

    private final IRegraDesconto regraDesconto =  (cliente, dias, total) -> {
        if (cliente instanceof PessoaFisica && dias > 5) return total * 0.05;
        if (cliente instanceof PessoaJuridica && dias > 3) return total * 0.10;

        return 0.0;
    };

    private double calcularDesconto(Cliente cliente, long dias, double total){
        return regraDesconto.aplicar(cliente,dias, total);
    }

    public List<Aluguel> buscarAluguelPorCliente(String nomeCliente) {
        return alugueis.stream()
                .filter(a -> a.getCliente().getNome().equalsIgnoreCase(nomeCliente))
                .sorted(Comparator.comparing(Aluguel::getRetirada))
                .collect(Collectors.toList());
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }
}
