package src.services;

import java.util.List;

public interface IBuscavel<T> {
    List<T> buscarPorNome(String parteNome);
}

