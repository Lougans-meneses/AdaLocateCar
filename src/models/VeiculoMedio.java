package src.models;

public class VeiculoMedio extends Veiculo {
    public VeiculoMedio(String placa, String modelo) {
        super(placa, modelo);
    }

    @Override
    public double getValorDiaria() {
        return 150.0;
    }
}
