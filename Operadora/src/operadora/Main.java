package operadora;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws ParseException  {
        final String NOME = "cliente.txt";
        final int FIM = 0;
        Arquivo arquivo = new Arquivo(NOME);
        ArrayList<Cliente> clientes = new ArrayList<>();
        int opcao;
        Scanner entrada;
        Formatter saida;
        Locale.setDefault(Locale.US);
        
        entrada = arquivo.abrirArquivo();
        if (entrada != null) {
            arquivo.leArquivo(entrada, clientes);
            arquivo.fecharArquivo(entrada);
        }
        else {
            JOptionPane.showMessageDialog(null, "Vazio!");
        }
        opcao = menu();
        while (opcao != FIM) {
            switch (opcao) {
                case 1: 
                    inclusao(clientes);
                    break;
                case 2: 
                    alteracao(clientes);
                    break;
                case 3: 
                    exclusao(clientes);
                    break;
                case 4: 
                    relatorioGerenciais(clientes);
                    break;
            }
            opcao = menu();
        }
        saida = arquivo.abreArquivoGravar();
        if (saida != null) {
            arquivo.gravaArquivo(saida, clientes);
            arquivo.fecharArquivo(saida);
        }
    }
    
    public static void inclusao(ArrayList<Cliente> clientes) {
        String nomeCliente;
        int numCelular, tipoPlano, cont = 0;
        double numCreditos;
        
        numCelular = leNumCelular();
        int pos = pesquisaCliente(numCelular, clientes);
        if (pos != -1) {
            JOptionPane.showMessageDialog(null, "Erro: Cliente já cadastrado!");
            return;
        }
        nomeCliente = leNomeCliente();
        tipoPlano = validaPlano();
        numCreditos = validaNumCredito();
        clientes.add(new Cliente(nomeCliente, numCelular, tipoPlano, numCreditos));
    }
    
    public static void alteracao(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: Vazio.");
            return;
        }
        int numCelular = leNumCelular();
        int pos = pesquisaCliente(numCelular, clientes);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            return;
        }
        String nomeCliente = leNomeCliente();
        int tipoPlano = validaPlano();
        double numCredito = validaNumCredito();
        clientes.get(pos).setNomeCliente(nomeCliente);
        clientes.get(pos).setTipoPlano(tipoPlano);
        clientes.get(pos).setNumCreditos(numCredito);
    }
    
    public static void exclusao(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: vazio!");
            return;
        }
        int numCelular = leNumCelular();
        int pos = pesquisaCliente(numCelular, clientes);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Número não encontrado!");
            return;
        }
        clientes.remove(pos);
    }
    
    public static void relatorioGerenciais(ArrayList<Cliente> clientes) throws ParseException {
        final String NOME = "ligacoes.txt";
        final int FIM = 0;
        Arquivo arquivo = new Arquivo(NOME);
        ArrayList<Ligacoes> ligacoes = new ArrayList<>();
        int opcao;
        Scanner entrada;
        Formatter saida;
        
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: vazio!");
            return;
        }
        entrada = arquivo.abrirArquivo();
        if (entrada != null) {
            arquivo.leArquivoLigacao(entrada, ligacoes);
            arquivo.fecharArquivo(entrada);
        }
        else {
            JOptionPane.showMessageDialog(null, "Vazio!");
        }
        opcao = menuRelatorios();
        while (opcao != FIM) {
            switch (opcao) {
                case 1: 
                    listar(clientes);
                    break;
                case 2: 
                    listarClientesCreditos(clientes);
                    break;
                case 3: 
                    listarClientesCreditosAcima(clientes);
                    break;
                case 4:
                    contaComMaiorCredito(clientes);
                    break;
                case 5: 
                    adicionarLigacao(clientes,ligacoes);
                    break;
                case 6: 
                    gerarBoleto(clientes,ligacoes);
                    break;
            }
            opcao = menuRelatorios();
        }
        saida = arquivo.abreArquivoGravar();
        if (saida != null) {
            arquivo.gravaArquivoLigacao(saida, ligacoes);
            arquivo.fecharArquivo(saida);
        }
    }
    
    public static void listar(ArrayList<Cliente> clientes) {
        for (int i = 0; i < clientes.size(); i++) {
            JOptionPane.showMessageDialog(null, clientes.get(i));
        }
    }
    
    public static void listarClientesCreditos(ArrayList<Cliente> clientes) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNumCreditos() >= 0) {
                JOptionPane.showMessageDialog(null, clientes.get(i));
            }
        }
    }
    
    public static void listarClientesCreditosAcima(ArrayList<Cliente> clientes) {
        double x = Double.parseDouble(JOptionPane.showInputDialog(null, "Entre com um valor para x: "));
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNumCreditos() > x) {
                JOptionPane.showMessageDialog(null, clientes.get(i));
            }
        }
    }
    
    public static void contaComMaiorCredito(ArrayList<Cliente> clientes) {
        double maior = Double.MIN_VALUE;
        Cliente c = null;
        
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNumCreditos() > maior) {
                maior = clientes.get(i).getNumCreditos();
                c = clientes.get(i);
            }
        }
        JOptionPane.showMessageDialog(null, c);
    }
    
    public static void adicionarLigacao(ArrayList<Cliente> clientes, ArrayList<Ligacoes> ligacoes) throws ParseException {
        final double preco = 1;
        Ligacoes l;
        String hora1, hora2;
        
        int numCelular = leNumCelular();
        int pos = pesquisaCliente(numCelular, clientes);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            return;
        }
        hora1 = JOptionPane.showInputDialog(null, "Informe a hora inicial (hh:mm:ss)");
        hora2 = JOptionPane.showInputDialog(null, "Informe a hora final (hh:mm:ss)");
        l = new Ligacoes(numCelular, hora1, hora2);
        ligacoes.add(l);
    }
    
    public static void gerarBoleto(ArrayList<Cliente> clientes, ArrayList<Ligacoes> ligacoes) throws ParseException {
        final double preco = 1;
        double boleto = 0;
        
        int numCelular = leNumCelular();
        int pos = pesquisaCliente(numCelular, clientes);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            return;
        }
        if (clientes.get(pos).getTipoPlano() == 1) {
            for (int i = 0; i < ligacoes.size(); i++) {
                if (ligacoes.get(i).getNumCelular() == clientes.get(pos).getNumeroCelular()) {
                    boleto = boleto + Math.ceil(diferencaHoras(ligacoes.get(i).getHoraInicio(), ligacoes.get(i).getHoraFim()));
                }
            }
            boleto = clientes.get(pos).getNumCreditos() - boleto;
            clientes.get(pos).setNumCreditos(boleto);
        }
        else if (clientes.get(pos).getTipoPlano() == 2) {
            for (int i = 0; i < ligacoes.size(); i++) {
                if (ligacoes.get(i).getNumCelular() == clientes.get(pos).getNumeroCelular()){
                    boleto = boleto + (Math.ceil(diferencaHoras(ligacoes.get(i).getHoraInicio(), ligacoes.get(i).getHoraFim())) * preco);
                }
            }
            JOptionPane.showMessageDialog(null, "Total da conta: " + boleto);
            clientes.get(pos).setNumCreditos(boleto);
        }
    }
    
    public static double diferencaHoras(String horaInicio, String horaFim) throws ParseException {
        double minutos;
        SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm:ss");
        Date horaI = sdFormat.parse(horaInicio);
        Date horaF = sdFormat.parse(horaFim);
        double horaInicial = horaI.getTime();
        double horaFinal = horaF.getTime();
        
        minutos = (double) (horaFinal - horaInicial) / 60000;
        return minutos;
    }
    
    public static int menu() {
        int opcao;
       
        do {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(null, "Selecione uma opção:\n [1] - Inclusão\n [2] - Alteração\n [3] - Exclusão\n [4] - Relatórios Gerenciais\n [0] - Sair"));
            if ((opcao < 0) || (opcao > 4)) {
                JOptionPane.showMessageDialog(null, "Operação inválida!");
            }
        } while ((opcao < 0) || (opcao > 4)); 
        return opcao;
    }
    
    public static int menuRelatorios() {
        int opcao;
       
        do {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(null, "Selecione uma opção:\n [1] - Listar clientes\n [2] - Listar clientes com créditos\n [3] - Listar clientes com créditos acima de x\n [4] - Conta com maior valor de crédito\n [5] - Adicionar Ligação\n [6] - Gerar boleto\n [0] - Voltar"));
            if ((opcao < 0) || (opcao > 6)) {
                JOptionPane.showMessageDialog(null, "Operação inválida!");
            }
        } while ((opcao < 0) || (opcao > 6)); 
        return opcao;
    }
    
    public static int pesquisaCliente(int numCelular, ArrayList<Cliente> clientes) {
        int pos = -1;
        
        for (int i = 0; i < clientes.size(); i++) { 
            if (clientes.get(i).getNumeroCelular() == numCelular) {
                pos = i;
                break;
            }
        }
        return pos;
    }
     
    public static int leNumCelular() {
       int numCelular = 0;
       boolean numero = false;
       
       do {
            try { 
                numCelular = Integer.parseInt(JOptionPane.showInputDialog("Entre com o número de celular: "));
                while ((numCelular < 10000000) || (numCelular > 99999999)) {
                    JOptionPane.showMessageDialog(null, "Erro: Número de celular inválido!");
                    numCelular = Integer.parseInt(JOptionPane.showInputDialog("Entre com o número de celular: "));
                }
                numero = true;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor entre com um número!");
            }
       } while (!numero);    
       return numCelular;
    }
    
    public static String leNomeCliente() {
        String nomeCliente;
        
        nomeCliente = JOptionPane.showInputDialog(null, "Entre com o nome do cliente: ");
        while (!nomeCliente.matches("[A-Z a-z]*")) {
            JOptionPane.showMessageDialog(null, "Erro: Entre com letras!");
            nomeCliente = JOptionPane.showInputDialog(null, "Entre com o nome do cliente: ");
        }
        return nomeCliente;
    }
    
    public static int validaPlano() {
        int tipoPlano = 0;
        boolean validacao = false;
        
        do {
            try { 
                tipoPlano = Integer.parseInt(JOptionPane.showInputDialog(null, "Entre com o plano: [1] - Pré-pago | [2] - Pós-pago"));
                while ((tipoPlano < 1) || (tipoPlano > 2)) {
                    JOptionPane.showMessageDialog(null, "Erro: tipo de plano inválido!");
                    tipoPlano = Integer.parseInt(JOptionPane.showInputDialog(null, "Entre com o plano: [1] - Pré-pago | [2] - Pós-pago"));
                }
                validacao = true;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor entre com o tipo de plano!");
            }
        } while (!validacao);
        return tipoPlano;
    }
    
    public static double validaNumCredito() {
        double numCreditos = 0;
        boolean validacao = false;
        
        do {
            try { 
                numCreditos = Double.parseDouble(JOptionPane.showInputDialog(null, "Entre com os créditos: "));
                while (numCreditos < 0) {
                    JOptionPane.showMessageDialog(null, "Erro: tipo de crédito inválido!");
                    numCreditos = Double.parseDouble(JOptionPane.showInputDialog(null, "Entre com os créditos: "));
                }
                validacao = true;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor entre com um número!");
            }
        } while (!validacao);
        return numCreditos;
    }
}