package src.models;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private boolean disponivel = true;

    public Veiculo(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
        this.disponivel = true;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public abstract double getValorDiaria();
}
