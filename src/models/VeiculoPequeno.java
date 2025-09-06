package src.models;

public class VeiculoPequeno extends Veiculo{
    public VeiculoPequeno(String placa, String modelo) {
        super(placa, modelo);
    }

    @Override
    public double getValorDiaria() {
        return 100.0;
    }
}
