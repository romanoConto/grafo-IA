package com.grafo.gerenciador;

import com.grafo.carregarDados.GerarMatrizNxN;
import com.grafo.carregarDados.LerEntradas;
import com.grafo.entregas.calculoIA.EntregasIA;
import com.grafo.entregas.calculoProfundidade.Entregas;
import com.grafo.entregas.calculoProfundidade.Rota;
import com.grafo.models.Entradas;
import com.grafo.models.RotasEntrega;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GerenciadorSimples {

    private static Entradas entradas = new Entradas();
    private static List<RotasEntrega> rotas = new ArrayList<>();
    private static int cores = Runtime.getRuntime().availableProcessors();
    private static Date start;
    private static Date finish;

    private static String diretorio = null;

    /**
     * Construtor da classe que executa o menu
     */

    public GerenciadorSimples() throws Exception {

        Scanner ler = new Scanner(System.in);
        int opcaoMenu;
        while (true) {

            try {
                System.out.println("\n=================== =================== LEILÃO DE ENTREGAS =================== ===================");
                System.out.println("1 - Carregar Entradas ");
                System.out.println("2 - Calcular Entregas Busca Profunda");
                System.out.println("6 - Mostrar Rotas ");
                System.out.println("7 - Limpar tela ");
                System.out.println("0 - Sair ");
                opcaoMenu = ler.nextInt();

                switch (opcaoMenu) {
                    case 1:
                        menuArquivos();
                        break;
                    case 2:
                        calcularRota();
                        break;
                    case 6:
                        mostrarRota();
                        break;
                    case 7:
                        limparTela();
                        break;
                    case 0:
                        System.out.println("Saindo ...");
                        System.exit(0);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ops! Ocorreu algo errado, tente novamente.");
            }
        }
    }

    private void menuArquivos() {
        Scanner ler = new Scanner(System.in);
        int opcaoMenuArquivos;
        System.out.println("\n=================== =================== ESCOLHA UMA OPÇÃO =================== ===================");
        System.out.println("1 - Carregar Entradas Enunciado ");
        System.out.println("2 - Carregar Entradas 2");
        System.out.println("3 - Carregar Bug Parametro ");
        System.out.println("4 - Carregar Bug Aleatorio ");
        System.out.println("5 - Carregar Bug Complexa ");
        System.out.println("0 - Voltar ");
        opcaoMenuArquivos = ler.nextInt();

        switch (opcaoMenuArquivos) {
            case 1:
                diretorio = "src\\files\\entradas.txt";
                ReadFile();
                break;

            case 2:
                diretorio = "src\\files\\entradas2.txt";
                ReadFile();
                break;

            case 3:
                diretorio = "src\\files\\bug_parametro.txt";
                ReadFile();
                break;

            case 4:
                diretorio = "src\\files\\bug_aleatorio.txt";
                ReadFile();
                break;

            case 5:
                diretorio = "src\\files\\bug_complexa.txt";
                ReadFile();
                break;

            case 0:
                return;
        }
    }

    /**
     * Mostra as rotas alternativas e a rota principal
     */

    private void mostrarRota() {
        int cont = 1;
        int recompensa = 0;
        System.out.println("\n=================== =================== #Entregas do dia# =================== ===================");
        for (RotasEntrega re : rotas) {
            Rota r = re.getRotaMenor();

            System.out
                    .print("\n\n=================== =================== A " + cont + "º rota a ser realizada é de 'A' até '" + r.getDestino() + "' =================== ===================");


            boolean isTrue = false;
            if (r.getRecompensa() == 1) {
                isTrue = true;
            }

            System.out.println("\n\nA rota Principal é: " + printRoute(r));
            System.out.println(
                    "Com a chegada estimada de " + r.getDistancia() + " unidades de tempo no destino " + "'"
                            + r.getDestino() + "'" + " e o valor para esta entrega será de " + (isTrue ?
                            r.getRecompensa() + " real" : r.getRecompensa() + " reais") + ".");


            recompensa += r.getRecompensa();
            cont++;
        }
        System.out.println("\n\nO lucro total do dia: " + recompensa + ".");
    }

    /**
     * Monta a tela da principal rota
     */

    private String printRoute(Rota r) {
        StringBuilder s = new StringBuilder();

        for (String d : r.getPontos()) {
            s.append(d + "->");
        }
        s = s.replace(s.length() - 2, s.length(), ".");

        return s.toString();
    }

    private static void limparTela() throws IOException {
        for (int i = 0; i < 100; ++i)
            System.out.println();
    }

    /**
     * Faz a leitura do arquivo, caso não seja possivel ler lança exception ao user
     */

    private static void ReadFile() {
        try {
            LerEntradas read = new LerEntradas();
            entradas = read.lerArquivoTxt(diretorio);
        } catch (Exception e) {
            System.out.println("Formato de arquivo inválido!");
        }
    }

    /**
     * Faz o calculo das entregas retornando as rotas
     */
    private static void calcularRota() throws CloneNotSupportedException {
        Entregas matriz = new Entregas(entradas);
        rotas = matriz.processarEntregas();
    }
}


