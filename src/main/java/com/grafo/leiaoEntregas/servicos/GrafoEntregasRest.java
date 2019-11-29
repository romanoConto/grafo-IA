package com.grafo.leiaoEntregas.servicos;

import com.grafo.leiaoEntregas.controlers.GrafoController;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/grafo-entregas")
public class GrafoEntregasRest {

    @GET
    @Path("/getGrafo/{path}/{name}")
    @Produces("application/json")
    public Response getGrafo(@PathParam("path") String path, @PathParam("name") String name) {
        try {
            GrafoController gc = new GrafoController(path, name);
            JSONObject grafo = gc.getGrafo();
            return Response.ok(grafo.toString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/getRoutes/{path}/{name}")
    @Produces("application/json")
    public Response getRoutes(@PathParam("path") String path, @PathParam("name") String name, @QueryParam("method") Integer method) {
        try {
            method = method == null || method > 3 || method < 0 ? 2 : method;
            GrafoController gc = new GrafoController(path, name);
            JSONObject grafo = gc.getRoutes(method);
            return Response.ok(grafo.toString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
