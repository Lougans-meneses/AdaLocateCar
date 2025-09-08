package src.services;

import src.models.*;
import java.util.*;

public class ClienteService {
    private Map<String, Cliente> clientes = new HashMap<>();

    public boolean cadastrarCliente(Cliente cliente) {
        if (clientes.containsKey(cliente.getDocumento())) {
            return false;
        }
        clientes.put(cliente.getDocumento(), cliente);
        return true;
    }

    public boolean alterarCliente(String documento, String novoNome) {
        Cliente c = clientes.get(documento);
        if (c == null) return false;
        c.setNome(novoNome);
        return true;
    }

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
