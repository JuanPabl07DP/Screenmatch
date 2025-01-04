package com.alura.screenmatch.principal;

import com.alura.screenmatch.excepcion.ErrorEnConversionDeDuracionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmbd;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Escriba el nombre de la pelicula: ");
        var busqueda = lectura.nextLine();

        String direccion = "https://www.omdbapi.com/?t=" + busqueda.replace(" ", "+") + "&apikey=7d1adf75";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            TituloOmbd miTituloOmbd = gson.fromJson(json, TituloOmbd.class);
            System.out.println(miTituloOmbd);

            Titulo miTitulo = new Titulo(miTituloOmbd);
            System.out.println("Título ya convertido: " + miTitulo);
        }catch (NumberFormatException e){
            System.out.println("Ocurrió un error: ");
            System.out.println(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("Error en la URI, verifique la dirección.");
        }catch (ErrorEnConversionDeDuracionException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Fin del programa");
    }
}
