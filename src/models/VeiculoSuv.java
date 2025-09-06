package src.models;

public class VeiculoSuv extends Veiculo {
    public VeiculoSuv(String placa, String modelo) {
        super(placa, modelo);
    }

    @Override
    public double getValorDiaria() {
        return 200.0;
    }
}
