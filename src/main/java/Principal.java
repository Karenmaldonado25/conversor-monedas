import com.google.gson.Gson;
import java.util.Scanner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException{


        Scanner lectura = new Scanner(System.in);
        String apiKey = "38c6a967b1984ab863f98c5c";
        int opcion = 0;

        while (opcion != 7) {

            System.out.println("""
*************************************************
Bienvenidos al Conversor de Monedas 💱

1) Dólar  => Peso argentino
2) Peso argentino => Dólar
3) Dólar => Real brasileño
4) Real brasileño => Dólar
5) Dólar => Peso colombiano
6) Peso colombiano => Dólar
7) Salir

Seleccione una opción:
*************************************************
""");

            opcion = lectura.nextInt();

            String monedaBase = "";
            String monedaDestino = "";

            switch (opcion) {

                case 1 -> { monedaBase = "USD"; monedaDestino = "ARS"; }
                case 2 -> { monedaBase = "ARS"; monedaDestino = "USD"; }
                case 3 -> { monedaBase = "USD"; monedaDestino = "BRL"; }
                case 4 -> { monedaBase = "BRL"; monedaDestino = "USD"; }
                case 5 -> { monedaBase = "USD"; monedaDestino = "COP"; }
                case 6 -> { monedaBase = "COP"; monedaDestino = "USD"; }
                case 7 -> {
                    System.out.println("\nGracias por utilizar el Conversor de Monedas 😊");
                    break;
                }
                default -> {
                    System.out.println("Opción inválida.");
                    continue;
                }
            }

            if (opcion == 7) {
                break;
            }

            System.out.println("Ingrese la cantidad a convertir:");
            double cantidad = lectura.nextDouble();

            String direccion = "https://v6.exchangerate-api.com/v6/"
                    + apiKey + "/latest/" + monedaBase;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            RespuestaAPI respuesta = gson.fromJson(response.body(), RespuestaAPI.class);

            double tasa = respuesta.getConversion_rates().get(monedaDestino);
            double resultado = cantidad * tasa;

            System.out.println("\nResultado:");
            System.out.println(cantidad + " " + monedaBase +
                    " equivalen a " + resultado + " " + monedaDestino);
            System.out.println();
        }

        lectura.close();
    }
}
