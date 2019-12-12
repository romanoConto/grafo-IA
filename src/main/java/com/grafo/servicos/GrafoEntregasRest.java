package com.grafo.servicos;

import com.grafo.carregarDados.LerEntradas;
import com.grafo.controlers.GrafoController;
import com.grafo.entregas.calculoIA.EntregasIA;
import com.grafo.entregas.calculoProfundidade.Entregas;
import com.grafo.models.Entradas;
import com.grafo.models.RotasEntrega;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/grafo")
public class GrafoEntregasRest {
    @POST
    @Path("/upload")
    @Produces("application/json")
    public Response upload(InputStream uploadedInputStream) throws Exception
    {
        LerEntradas read = new LerEntradas();
        Entradas entradas;
        java.nio.file.Path tempFile = Files.createTempFile("input", ".txt");

        Files.copy(uploadedInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        entradas = read.lerArquivoTxt( tempFile.toString());

        EntregasIA matriz = new EntregasIA(entradas);
        List<RotasEntrega> rotas = matriz.processarEntregas();

        JSONObject json = new JSONObject();
        json.put("entrada", new JSONObject(entradas));
        json.put("rotas", new JSONArray(rotas));
        return Response.ok(json.toString()).build();
    }

}
