package com.grafo.leiaoEntregas.gerenciador;

import com.grafo.leiaoEntregas.carregarDados.GerarMatrizNxN;
import com.grafo.leiaoEntregas.entregas.calculoIA.EntregasIA;
import com.grafo.leiaoEntregas.entregas.calculoProfundidade.Entregas;
import com.grafo.leiaoEntregas.entregas.calculoProfundidade.Rota;
import com.grafo.leiaoEntregas.models.Entradas;
import com.grafo.leiaoEntregas.carregarDados.LerEntradas;
import com.grafo.leiaoEntregas.models.RotasEntrega;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Gerenciador {

    private static Entradas entradas = new Entradas();
    private static List<RotasEntrega> rotas = new ArrayList<>();
    private static int cores = Runtime.getRuntime().availableProcessors();
    private static Date start;
    private static Date finish;

    private static String path = null;

    /**
     * Construtor da classe que executa o menu
     */

    public Gerenciador() throws Exception {

        Scanner ler = new Scanner(System.in);
        int iniciar;
        while (true) {

            try {
                System.out.println("\n=================== =================== LEILÃO DE ENTREGAS =================== ===================");
                System.out.println("1 - Carregar Entradas ");
                System.out.println("2 - Calcular Entregas Busca Profunda");
                System.out.println("3 - Calcular Entregas Busca Profunda + Threads");
                System.out.println("4 - Calcular Entregas IA");
                System.out.println("5 - Calcular Entregas IA + Threads");
                System.out.println("6 - Mostrar Rotas ");
                System.out.println("7 - Limpar tela ");
                System.out.println("0 - Sair ");
                iniciar = ler.nextInt();

                switch (iniciar) {
                    case 1:
                        menuArquivos();
                        break;
                    case 2:
                        calcularRota();
                        break;
                    case 3:
                        calcRouteThread();
                        break;
                    case 4:
                        calcularRotaIA();
                        break;
                    case 5:
                        calcRouteIAThread();
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
        int iniciar;
        System.out.println("\n=================== =================== ESCOLHA UMA OPÇÃO =================== ===================");
        System.out.println("1 - Carregar Entradas Enunciado ");
        System.out.println("2 - Carregar Bug Parametro ");
        System.out.println("3 - Carregar Bug Aleatorio ");
        System.out.println("4 - Carregar Bug Complexa ");
        System.out.println("5 - Carregar Entradas 2");
        System.out.println("6 - Matriz Over Power");
        System.out.println("0 - Voltar ");
        iniciar = ler.nextInt();

        switch (iniciar) {
            case 1:
                path = "src\\files\\entradas.txt";
                ReadFile();
                break;

            case 2:
                path = "src\\files\\bug_parametro.txt";
                ReadFile();
                break;

            case 3:
                path = "src\\files\\bug_aleatorio.txt";
                ReadFile();
                break;

            case 4:
                path = "src\\files\\bug_complexa.txt";
                ReadFile();
                break;

            case 5:
                path = "src\\files\\entradas2.txt";
                ReadFile();
                break;

            case 6:
                System.out.println("Informe o tamanho da matriz:");
                iniciar = ler.nextInt();
                System.out.println("Informe a complexidade da matriz.");
                System.out.println("1 a 25 pouco complexo, 26 a 50 complexo, 51 a 75 muito complexo.");
                Float f = ler.nextFloat();
                entradas = GerarMatrizNxN.getMatriz(iniciar, f);
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

            if (re.getRotas() != null && re.getRotas().size() > 0) {
                System.out.print("\nAs rotas alternativas são:" + getAlternativeRoutes(re.getRotas()));
            }

            recompensa += r.getRecompensa();
            cont++;
        }
        System.out.println("\n\nO lucro total do dia: " + recompensa + ".");
        System.out.println("Tempo de calculo: " + (finish.getTime() - start.getTime()) + "ms.");

    }

    /**
     * Monta a tela de rotas alternativas
     */

    private String getAlternativeRoutes(List<Rota> rotas) {
        StringBuilder sb = new StringBuilder();

        for (Rota r : rotas) {
            sb.append("\nCusto = " + r.getDistancia() + ".  Rota: " + printRoute(r));
        }
        return sb.toString();
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

    /**
     * Faz a leitura do arquivo, caso não seja possivel ler lança exception ao user
     */

    private static void ReadFile() {
        try {
            LerEntradas read = new LerEntradas();
            entradas = read.lerArquivoTxt(path);
        } catch (Exception e) {
            System.out.println("Formato de arquivo inválido!");
        }
    }

    /**
     * Faz o calculo das entregas retornando as rotas
     */

    private static void calcularRota() throws CloneNotSupportedException {
        start = new Date();
        Entregas matriz = new Entregas(entradas);
        rotas = matriz.processarEntregas();
        finish = new Date();
    }

    private static void calcRouteThread() throws CloneNotSupportedException {
        start = new Date();
        Entregas matriz = new Entregas(entradas);
        rotas = matriz.processarEntregasThread();
        finish = new Date();
    }

    private static void calcularRotaIA() throws CloneNotSupportedException {
        start = new Date();
        EntregasIA matriz = new EntregasIA(entradas);
        rotas = matriz.processarEntregas();
        finish = new Date();
    }

    private static void calcRouteIAThread() throws CloneNotSupportedException {
        start = new Date();
        EntregasIA matriz = new EntregasIA(entradas);
        rotas = matriz.processaEntregasThread();
        finish = new Date();
    }

    private static void limparTela() throws IOException {
        for (int i = 0; i < 100; ++i)
            System.out.println();
    }
}


