package com.grafo.gerenciador;

import com.grafo.carregarDados.LerEntradas;
import com.grafo.entregas.calculoProfundidade.Entregas;
import com.grafo.entregas.calculoProfundidade.Rota;
import com.grafo.models.Entradas;
import com.grafo.models.MelhorEntrega;
import com.grafo.models.RotasEntrega;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GerenciadorSimples {

    private static Entradas entradas;
    private static List<RotasEntrega> rotas;
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
                if (entradas != null) {
                    System.out.println("2 - Calcular Entregas");
                    if (rotas != null) {
                        System.out.println("3 - Mostrar Rotas ");
                    }
                }
                System.out.println("4 - Limpar tela ");
                System.out.println("0 - Sair ");
                opcaoMenu = ler.nextInt();

                switch (opcaoMenu) {
                    case 1:
                        menuArquivos();
                        break;
                    case 2:
                        calcularRota();
                        break;
                    case 3:
                        mostrarRota();
                        break;
                    case 4:
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

            System.out.println("\nA rota menor é: " + printRoute(r));
            System.out.println(
                    "Com a chegada estimada de " + r.getDistancia() + " unidades de tempo no destino " + "'"
                            + r.getDestino() + "'" + " e o valor para esta entrega será de " + (isTrue ?
                            r.getRecompensa() + " real" : r.getRecompensa() + " reais") + ".");


            recompensa += r.getRecompensa();
            cont++;
        }
        calculaCaminhoMaisLucrativo();
    }

    /**
     * Monta a tela da principal rota
     */

    private static String printRoute(Rota r) {
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

    private static void calculaCaminhoMaisLucrativo() {
        try {

            //pega a lista de menores rotas
            List<Rota> menoresRotas = rotas.stream().map(x -> x.getRotaMenor()).collect(Collectors.toList());


            //crita uma nova lista de melhor entrega seta os valores inicias
            MelhorEntrega melhorEntrega = new MelhorEntrega();
            melhorEntrega.setBonusTotal(0);
            melhorEntrega.setEntregas(new ArrayList<>());
            melhorEntrega.setTempoAtual(0);

            melhorEntrega = calculaMelhorEntrega(melhorEntrega, menoresRotas, null);
            mostraMelhorSeguenciaEntrega(melhorEntrega);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Monta mensagem custumizada para o usuario na tela
     */

    private static void mostraMelhorSeguenciaEntrega(MelhorEntrega melhorEntrega) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("========================================================== Melhores Entregas para Obter o Maior Bonus =====================================================");

        for (Rota rota : melhorEntrega.getEntregas()) {
            sb.append("\n");
            sb.append("Deve partir no tempo ");
            sb.append(rota.getPartida());
            sb.append(" para fazer a entrega ");
            sb.append(printRoute(rota));
            sb.append(" Recebe o bonus de ");
            sb.append(rota.getRecompensa());
        }
        sb.append("\n\nO lucro total do dia: " + melhorEntrega.getBonusTotal() + ".");
        System.out.print(sb);
        System.out.println("\n\nAperte enter para continuar.");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.in.read();
        limparTela();
    }

    /**
     * calcular a melhor sequencia de entrega para obter o maior lucro
     */

    private static MelhorEntrega calculaMelhorEntrega(MelhorEntrega melhorEntrega, List<Rota> pendentes, Rota pendenteAnterior) {

        // se a rota anterior não é nula
        if (pendenteAnterior != null) {
            melhorEntrega.addEntregas(pendenteAnterior);
            melhorEntrega.setTempoAtual(pendenteAnterior.getPartida() + (pendenteAnterior.getDistancia() * 2));
            melhorEntrega.addBonus(pendenteAnterior.getRecompensa());
        }

        // lista que guarda as melhores entregas
        List<MelhorEntrega> melhoresEntregas = new ArrayList<>();

        //verifica rota a rota que ainda estão pedentes de verificação
        for (Rota pendente : pendentes) {

            //verifica se é possivel realizar a entrega a tempo
            if (melhorEntrega.getTempoAtual() <= pendente.getPartida()) {
                MelhorEntrega novoMelhorEntrega = new MelhorEntrega();
                novoMelhorEntrega.setTempoAtual(melhorEntrega.getTempoAtual());
                novoMelhorEntrega.setBonusTotal(melhorEntrega.getBonusTotal());
                novoMelhorEntrega.setEntregas(new ArrayList<>(melhorEntrega.getEntregas()));

                //cria uma copia das rotas pendentes
                List<Rota> novoPendentes = new ArrayList<>(pendentes);
                //remove a rota atual da lista
                novoPendentes.remove(pendente);

                //calcula a melhor sequencia de entrega a partir do ponto já calculado
                MelhorEntrega retornoMelhorEntrega = calculaMelhorEntrega(novoMelhorEntrega, novoPendentes, pendente);

                if (retornoMelhorEntrega != null)
                    melhoresEntregas.add(retornoMelhorEntrega);
                else
                    melhoresEntregas.add(novoMelhorEntrega);
            }
        }

        //ordena do maior para o menor bonus
        melhoresEntregas.sort((x, y) -> Integer.compare(y.getBonusTotal(), x.getBonusTotal()));

        //retorna a melhor entrega com melhor bonus
        return melhoresEntregas.isEmpty() ? null : melhoresEntregas.get(0);
    }
}


