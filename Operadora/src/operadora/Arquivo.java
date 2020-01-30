package operadora;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import javafx.scene.chart.PieChart;
import javax.swing.JOptionPane;

public class Arquivo {
    private final String nomeArquivo;
    
    public Arquivo(String nome) {
        nomeArquivo = nome;
    }
    
    public Scanner abrirArquivo() {
        Scanner entrada = null;
        
        try {
            entrada = new Scanner(new File(nomeArquivo));
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro: Abertura do arquivo");
        }
        return entrada;
    }
    
    public Formatter abreArquivoGravar() {
        Formatter saida = null;
        
        try {
            saida = new Formatter(new File(nomeArquivo));
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro: Abertura do arquivo");
        }
        return saida;
    }
    
    public void leArquivo(Scanner entrada, ArrayList<Cliente> clientes) {
        String linha;
        String[] campos;
        
        try {
            while (entrada.hasNext()) {
                linha = entrada.nextLine();
                campos = linha.split("/");
                clientes.add(new Cliente(campos[0], Integer.parseInt(campos[1]), Integer.parseInt(campos[2]), Double.parseDouble(campos[3])));
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Na leitura de Arquivo");
        }
    }
    
    public void leArquivoLigacao(Scanner entrada, ArrayList<Ligacoes> ligacoes) {
        String linha;
        String[] campos;
        
        
        try {
            while (entrada.hasNext()) {
                linha = entrada.nextLine();
                campos = linha.split("/");
                ligacoes.add(new Ligacoes(Integer.parseInt(campos[0]), campos[1], campos[2]));
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Na leitura de Arquivo");
        }
    }
    
    public void gravaArquivo(Formatter saida, ArrayList<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            try {
                saida.format("%s/%d/%d/%f%n", cliente.getNomeCliente(), cliente.getNumeroCelular(), cliente.getTipoPlano(), cliente.getNumCreditos());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: Gravar arquivo");
            }
        }
    }
    
    public void gravaArquivoLigacao(Formatter saida, ArrayList<Ligacoes> ligacoes) {
        for (Ligacoes ligacao : ligacoes) {
            try {
                saida.format("%d/%s/%s%n", ligacao.getNumCelular(), ligacao.getHoraInicio(), ligacao.getHoraFim());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: Gravar arquivo");
            }
        }
    }
    
    public void fecharArquivo(Scanner entrada) {
        if (entrada != null) {
            entrada.close();
        }
    }
    
    public void fecharArquivo(Formatter saida) {
        if (saida != null) {
            saida.close();
        }
    }
}
