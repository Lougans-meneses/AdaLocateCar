package src.services;

import src.models.*;
import java.util.*;

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
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente v : clientes.values()) {
            if (v.getNome().toLowerCase().contains(parteNome.toLowerCase())) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
