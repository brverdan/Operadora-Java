
package operadora;

public class Cliente {
    private int numeroCelular, tipoPlano;
    private String nomeCliente;
    private double numCreditos;

    public Cliente(String nomeCliente, int numeroCelular, int tipoPlano, double numCreditos) {
        this.numeroCelular = numeroCelular;
        this.nomeCliente = nomeCliente;
        this.tipoPlano = tipoPlano;
        this.numCreditos = numCreditos;
    }
    
    public Cliente(){
    }

    public int getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(int numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(int tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public double getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(double numCreditos) {
        this.numCreditos = numCreditos;
    }
    
    @Override
    public String toString() {
        return "Nome: " + nomeCliente + "\nNúmero de celular: " + numeroCelular + "\nTipo de plano: " + tipoPlano 
                + "\nQuantidade de créditos: " + numCreditos;
    }
}
