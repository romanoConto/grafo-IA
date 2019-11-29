package com.grafo.leiaoEntregas.controlers;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileController {

    public static JSONObject getFilesJSON(String path) {
        JSONObject filesJSON = new JSONObject();
        filesJSON.put("files", getFilesString(path));
        return filesJSON;
    }

    public static List<String> getFilesString(String path) {
        List<File> files = getFiles(path);

        return files.stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    public static List<File> getFiles(String path) {
        List<File> files = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
                files.add(file);
            }
        }
        return files;
    }
}
