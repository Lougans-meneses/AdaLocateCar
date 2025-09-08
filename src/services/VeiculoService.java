package src.services;

import src.models.*;
import java.util.*;

public class VeiculoService implements ICadastravel<Veiculo>, IAlteravel<Veiculo>, IBuscavel<Veiculo> {
    private Map<String, Veiculo> veiculos = new HashMap<>(); // placa como chave

    @Override
    public boolean cadastrar(Veiculo veiculo) {
        if (veiculos.containsKey(veiculo.getPlaca())) {
            return false;
        }
        veiculos.put(veiculo.getPlaca(), veiculo);
        return true;
    }

    @Override
    public boolean alterar(String placa, String novoModelo) {
        Veiculo v = veiculos.get(placa);
        if (v == null) return false;
        v.setModelo(novoModelo);
        return true;
    }

    @Override
    public List<Veiculo> buscarPorNome(String parteNome) {
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : veiculos.values()) {
            if (v.getModelo().toLowerCase().contains(parteNome.toLowerCase())) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public Veiculo buscarPorPlaca(String placa) {
        return veiculos.get(placa);
    }

    public void setDisponibilidade(String placa, boolean disponivel) {
        Veiculo v = veiculos.get(placa);
        if (v != null) {
            v.setDisponivel(disponivel);
        }
    }

    public boolean isDisponivel(String placa) {
        Veiculo v = veiculos.get(placa);
        return v != null && v.isDisponivel();
    }
}
