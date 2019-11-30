import com.grafo.gerenciador.Gerenciador;
import com.grafo.gerenciador.GerenciadorSimples;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner ler = new Scanner(System.in);
        int opcaoMenu;
        System.out.println("1 - Simples ");
        System.out.println("2 - Completo ");
        opcaoMenu = ler.nextInt();

        switch (opcaoMenu) {
            case 1:
                new GerenciadorSimples();
                break;
            case 2:
                new Gerenciador();
                break;
        }
    }
}
