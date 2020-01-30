package operadora;

public class Ligacoes {
    private int numCelular;
    private String horaInicio, horaFim;

    public Ligacoes(int numCelular, String horaInicio, String horaFim) {
        this.numCelular = numCelular;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
    
    public Ligacoes() {
    }

    public int getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(int numCelular) {
        this.numCelular = numCelular;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }
}    