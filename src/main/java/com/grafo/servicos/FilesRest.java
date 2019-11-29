package com.grafo.servicos;

import com.grafo.controlers.FileController;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/files")
public class FilesRest {

    @GET
    @Path("/getFiles/{path}")
    @Produces("application/json")
    public Response getFiles(@PathParam("path") String path)
    {
        try {
            JSONObject json = FileController.getFilesJSON(path);
            return Response.ok(json.toString()).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @GET
    @Produces("application/json")
    public Response get()
    {
        System.out.println("oiiii");
        try {
            JSONObject json = FileController.getFilesJSON(" ");
            return Response.ok(json.toString()).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
