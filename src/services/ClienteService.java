package src.services;

import src.models.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClienteService implements ICadastravel<Cliente>, IAlteravel<Cliente>, IBuscavel<Cliente> {
    private Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public boolean cadastrar(Cliente cliente) {
        if (clientes.containsKey(cliente.getDocumento())) {
            return false;
        }
        clientes.put(cliente.getDocumento(), cliente);
        return true;
    }

    @Override
    public boolean alterar(String documento, String novoNome) {
        Cliente c = clientes.get(documento);
        if (c == null) return false;
        c.setNome(novoNome);
        return true;
    }

    @Override
    public List<Cliente> buscarPorNome(String parteNome) {
        Predicate<Cliente> filtroPorNome = c ->
                c.getNome().toLowerCase().contains(parteNome.toLowerCase());

        return clientes.values().stream()
                .filter(filtroPorNome)
                .sorted(Comparator.comparing(Cliente::getNome))
                .map(c -> new Cliente(c.getNome().trim(), c.getDocumento()) {
                })
                .collect(Collectors.toList());
    }

    public void normalizarNomes() {
        Consumer<Cliente> normalizar = c -> c.setNome(c.getNome().trim());
        clientes.values().forEach(normalizar);
    }

    public Cliente buscarPorDocumento(String documento) {
        return clientes.get(documento);
    }

}
