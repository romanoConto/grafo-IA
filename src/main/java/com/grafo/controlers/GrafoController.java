package com.grafo.controlers;

import com.grafo.entregas.calculoProfundidade.Entregas;
import com.grafo.carregarDados.LerEntradas;
import com.grafo.entregas.calculoIA.EntregasIA;
import com.grafo.models.Entradas;
import com.grafo.models.RotasEntrega;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrafoController {

    private static Entradas entradas;
    private static List<RotasEntrega> rotas = new ArrayList<>();
    private String pathFile;
    private Date start;
    private Date finish;

    public GrafoController(String pathFile, String fileName) {
        this.pathFile = pathFile + "/" + fileName;
        ReadFile();
    }

    public JSONObject getGrafo() {
        JSONObject grafoJSON = new JSONObject();
        grafoJSON.put("grafo", entradas.getVerticesMatrizGrafo());
        return grafoJSON;
    }

    public JSONObject getRoutes(Integer method) throws CloneNotSupportedException {
        getRoute(method);

        JSONObject rotasJSON = new JSONObject();
        rotasJSON.put("rotas", rotas);
        return rotasJSON;
    }

    /**
     * Faz a leitura do arquivo, caso não seja possivel ler lança exception ao user
     */

    private void ReadFile() {
        try {
            LerEntradas read = new LerEntradas();
            entradas = read.lerArquivoTxt(pathFile);
        } catch (Exception e) {
            System.out.println("Formato de arquivo inválido!");
        }
    }

    /**
     * Faz o calculo das entregas retornando as rotas
     */

    private void getRoute(Integer method) throws CloneNotSupportedException {
        switch (method) {
            case 0:
                calcRoute();
                break;
            case 1:
                calcRouteThread();
                break;
            case 2:
                calcRouteIA();
                break;
            case 3:
                calcRouteIAThread();
                break;
        }
    }

    private void calcRoute() throws CloneNotSupportedException {
        start = new Date();
        Entregas matriz = new Entregas(entradas);
        rotas = matriz.processarEntregas();
        finish = new Date();
    }

    private void calcRouteThread() throws CloneNotSupportedException {
        start = new Date();
        Entregas matriz = new Entregas(entradas);
        rotas = matriz.processarEntregasThread();
        finish = new Date();
    }

    private void calcRouteIA() throws CloneNotSupportedException {
        start = new Date();
        EntregasIA matriz = new EntregasIA(entradas);
        rotas = matriz.processarEntregas();
        finish = new Date();
    }

    private void calcRouteIAThread() throws CloneNotSupportedException {
        start = new Date();
        EntregasIA matriz = new EntregasIA(entradas);
        rotas = matriz.processaEntregasThread();
        finish = new Date();
    }

}
