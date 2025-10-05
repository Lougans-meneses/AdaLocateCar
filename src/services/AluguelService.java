package src.services;

import src.models.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AluguelService {
    private List<Aluguel> alugueis = new ArrayList<>();

    public boolean alugarVeiculo(String local, LocalDateTime retirada, Veiculo veiculo, Cliente cliente) {
        if (!veiculo.isDisponivel()) return false;
        veiculo.setDisponivel(false);
        Aluguel aluguel = new Aluguel(local, retirada, veiculo, cliente);
        alugueis.add(aluguel);
        gerarComprovanteAluguel(aluguel);
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

    private double calcularDesconto(Cliente cliente, long dias, double total) {
        if (cliente instanceof PessoaFisica && dias > 5) {
            return total * 0.05;
        }
        if (cliente instanceof PessoaJuridica && dias > 3) {
            return total * 0.10;
        }
        return 0.0;
    }

    private void gerarComprovanteAluguel(Aluguel aluguel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String nomeArquivo = "aluguel_" + System.currentTimeMillis() + ".txt"; // nome único
        String caminho = "./" + nomeArquivo; // arquivo na raiz do projeto

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("==== Dados do Aluguel ====\n");
            writer.write("Local: " + aluguel.getLocal() + "\n");
            writer.write("Data/Hora Retirada: " + aluguel.getRetirada().format(formatter) + "\n");
            writer.write("\n--- Dados do Veículo ---\n");
            writer.write("Modelo: " + aluguel.getVeiculo().getModelo() + "\n");
            writer.write("Placa: " + aluguel.getVeiculo().getPlaca() + "\n");
            writer.write("\n--- Dados do Cliente ---\n");
            writer.write("Nome: " + aluguel.getCliente().getNome() + "\n");
            writer.write("Documento: " + aluguel.getCliente().getDocumento() + "\n");
            writer.write("==========================\n");
            System.out.println("Arquivo gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo de aluguel: " + e.getMessage());
        }
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }
}
