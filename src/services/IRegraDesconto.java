package src.services;

import src.models.Cliente;

@FunctionalInterface
public interface IRegraDesconto {
    double aplicar(Cliente cliente, long dias, double total);
}
