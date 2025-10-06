package src;

import src.models.*;
import src.services.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        VeiculoService veiculoService = new VeiculoService();
        ClienteService clienteService = new ClienteService();
        AluguelService aluguelService = new AluguelService();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            System.out.println("\n======================================");
            System.out.println("        LOCADORA DE VEÍCULOS");
            System.out.println("======================================");
            System.out.println("1 - Cadastrar veículo");
            System.out.println("2 - Alterar veículo");
            System.out.println("3 - Buscar veículo por nome");
            System.out.println("4 - Cadastrar cliente PF");
            System.out.println("5 - Cadastrar cliente PJ");
            System.out.println("6 - Alterar cliente PF");
            System.out.println("7 - Alterar cliente PJ");
            System.out.println("8 - Alugar veículo");
            System.out.println("9 - Devolver veículo");
            System.out.println("10 - Listar veículos cadastrados");
            System.out.println("11 - Listar clientes cadastrados");
            System.out.println("0 - Sair");
            int op;
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido!");
                continue;
            }
            if (op == 0) break;
            switch (op) {
                case 1:
                    System.out.print("Tipo (PEQUENO/MEDIO/SUV): ");
                    String tipo = sc.nextLine().toUpperCase();
                    System.out.print("Placa: ");
                    String placa = sc.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = sc.nextLine();
                    Veiculo v = null;
                    if (tipo.equals("PEQUENO")) v = new VeiculoPequeno(placa, modelo);
                    else if (tipo.equals("MEDIO")) v = new VeiculoMedio(placa, modelo);
                    else if (tipo.equals("SUV")) v = new VeiculoSuv(placa, modelo);
                    if (v != null && veiculoService.cadastrar(v))
                        System.out.println("Veículo cadastrado!");
                    else
                        System.out.println("Erro ao cadastrar (placa repetida ou tipo inválido)");
                    break;
                case 2:
                    System.out.print("Placa: ");
                    placa = sc.nextLine();
                    System.out.print("Novo modelo: ");
                    modelo = sc.nextLine();
                    if (veiculoService.alterar(placa, modelo))
                        System.out.println("Veículo alterado!");
                    else
                        System.out.println("Veículo não encontrado!");
                    break;
                case 3:
                    System.out.print("Parte do nome/modelo: ");
                    String parte = sc.nextLine();
                    for (Veiculo ve : veiculoService.buscarPorNome(parte)) {
                        System.out.println(ve.getPlaca() + " - " + ve.getModelo());
                    }
                    break;
                case 4:
                    System.out.print("Nome: ");
                    String nomePF = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    if (clienteService.buscarPorDocumento(cpf) != null) {
                        System.out.println("CPF já cadastrado!");
                    } else if (clienteService.cadastrar(new PessoaFisica(nomePF, cpf))) {
                        System.out.println("Cliente PF cadastrado!");
                    } else {
                        System.out.println("Erro ao cadastrar cliente!");
                    }
                    break;
                case 5:
                    System.out.print("Nome: ");
                    String nomePJ = sc.nextLine();
                    System.out.print("CNPJ: ");
                    String cnpj = sc.nextLine();
                    if (clienteService.buscarPorDocumento(cnpj) != null) {
                        System.out.println("CNPJ já cadastrado!");
                    } else if (clienteService.cadastrar(new PessoaJuridica(nomePJ, cnpj))) {
                        System.out.println("Cliente PJ cadastrado!");
                    } else {
                        System.out.println("Erro ao cadastrar cliente!");
                    }
                    break;
                case 6:
                    System.out.print("CPF: ");
                    cpf = sc.nextLine();
                    System.out.print("Novo nome: ");
                    nomePF = sc.nextLine();
                    if (clienteService.alterar(cpf, nomePF))
                        System.out.println("Cliente PF alterado!");
                    else
                        System.out.println("CPF não encontrado!");
                    break;
                case 7:
                    System.out.print("CNPJ: ");
                    cnpj = sc.nextLine();
                    System.out.print("Novo nome: ");
                    nomePJ = sc.nextLine();
                    if (clienteService.alterar(cnpj, nomePJ))
                        System.out.println("Cliente PJ alterado!");
                    else
                        System.out.println("CNPJ não encontrado!");
                    break;
                case 8:
                    System.out.print("Placa do veículo: ");
                    placa = sc.nextLine();
                    Veiculo veiculoAlugar = veiculoService.buscarPorPlaca(placa);
                    if (veiculoAlugar == null || !veiculoAlugar.isDisponivel()) {
                        System.out.println("Veículo não disponível!");
                        break;
                    }
                    System.out.print("Local de retirada: ");
                    String local = sc.nextLine();
                    System.out.print("Data/hora retirada (dd/MM/yyyy HH:mm): ");
                    LocalDateTime retirada = null;
                    while (retirada == null) {
                        String dtRet = sc.nextLine();
                        try {
                            retirada = LocalDateTime.parse(dtRet, dtf);
                        } catch (Exception e) {
                            System.out.print("Formato inválido! Tente novamente (dd/MM/yyyy HH:mm): ");
                        }
                    }
                    System.out.print("Cliente (PF ou PJ): ");
                    String tipoCli = sc.nextLine().toUpperCase();System.out.print(tipoCli.equals("PF") ? "CPF: " : "CNPJ: ");
                    String doc = sc.nextLine();
                    Cliente cliente = clienteService.buscarPorDocumento(doc);
                    if (cliente == null) {
                        System.out.println("Cliente não encontrado!");
                        break;
                    }
                    if (aluguelService.alugarVeiculo(local, retirada, veiculoAlugar, cliente))
                        System.out.println("Aluguel realizado!");
                    else
                        System.out.println("Erro ao alugar!");
                    break;
                case 9:
                    System.out.print("Placa do veículo: ");
                    placa = sc.nextLine();
                    Aluguel aluguel = aluguelService.getAlugueis().stream()
                            .filter(a -> a.getVeiculo().getPlaca().equals(placa) && a.getDevolucao() == null)
                            .findFirst()
                            .orElse(null);
                    if (aluguel == null) {
                        System.out.println("Aluguel não encontrado!");
                        break;
                    }
                    System.out.print("Data/hora devolução (dd/MM/yyyy HH:mm): ");
                    LocalDateTime devolucao = null;
                    while (devolucao == null) {
                        String dtDev = sc.nextLine();
                        try {
                            devolucao = LocalDateTime.parse(dtDev, dtf);
                        } catch (Exception e) {
                            System.out.print("Formato inválido! Tente novamente (dd/MM/yyyy HH:mm): ");
                        }
                    }
                    double valor = aluguelService.devolverVeiculo(aluguel, devolucao);
                    System.out.println("Valor total: R$ " + valor);
                    break;
                case 10:
                    System.out.print("Tamanho da página: ");
                    int tamanhoPaginaV = Integer.parseInt(sc.nextLine());
                    System.out.print("Número da página: ");
                    int paginaV = Integer.parseInt(sc.nextLine());
                    int paginaIndiceV = Math.max(0, paginaV - 1);
                    veiculoService.buscarPorNome("")
                        .stream()
                        .skip((long) paginaIndiceV * tamanhoPaginaV)
                        .limit(tamanhoPaginaV)
                        .forEach(ve ->
                            System.out.printf("%s - %s (%s)\n", ve.getPlaca(), ve.getModelo(), ve.isDisponivel() ? "Disponível" : "Alugado"));
                    break;
                case 11:
                    System.out.print("Tamanho da página: ");
                    int tamanhoPaginaC = Integer.parseInt(sc.nextLine());
                    System.out.print("Número da página: ");
                    int paginaC = Integer.parseInt(sc.nextLine());
                    int paginaIndiceC = Math.max(0, paginaC - 1);
                    clienteService.buscarPorNome("")
                        .stream()
                        .skip((long) paginaIndiceC * tamanhoPaginaC)
                        .limit(tamanhoPaginaC)
                        .forEach(c ->
                            System.out.printf("%s - %s\n", c.getDocumento(), c.getNome()));
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }
}
