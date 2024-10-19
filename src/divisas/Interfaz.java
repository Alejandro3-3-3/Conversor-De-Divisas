package divisas;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

class TasaCambio {
    private String base;
    private Rates rates; // Clase para contener las tasas de cambio

    public String getBase() {
        return base;
    }

    public Rates getRates() {
        return rates;
    }

    class Rates {
        private double COP; // Peso colombiano
        private double BRL; // Real brasileño
        private double EUR; // Euro

        public double getCOP() {
            return COP;
        }

        public double getBRL() {
            return BRL;
        }

        public double getEUR() {
            return EUR;
        }
    }
}

public class Interfaz {
    public static void main(String[] args) {
        int opcion = 0;

        System.out.println("*******************************");
        String menu = """
                Bienvenido/a al conversor de divisa
                1- Dólar           => Peso colombiano
                2- Peso colombiano => Dólar
                3- Dólar           => real brasileño
                4- Real brasileño  => Dólar
                5- Dólar           => Euro
                6- Euro            => Dólar
                7- Salir
                """;
        System.out.println(menu);

        Scanner teclado = new Scanner(System.in);
        TasaDeCambio tasaCambio = obtenerTasasDeCambio();

        while (opcion != 7) {
            System.out.println("Seleccione una opción");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    System.out.printf("El valor de 1 Dólar en Pesos colombianos es: %.2f%n", tasaCambio.getConversionRates().get("COP"));
                    break;
                case 2:
                    double valorEnDolares = 1 / tasaCambio.getConversionRates().get("COP");
                    System.out.printf("El valor de 1 Peso colombiano en dólares es: %.2f%n", valorEnDolares);
                    break;
                case 3:
                    System.out.printf("El valor de 1 Dólar en Reales brasileños es: %.2f%n", tasaCambio.getConversionRates().get("BRL"));
                    break;
                case 4:
                    double valorEnDolaresBRL = 1 / tasaCambio.getConversionRates().get("BRL");
                    System.out.printf("El valor de 1 Real brasileño en Dólares es: %.2f%n", valorEnDolaresBRL);
                    break;
                case 5:
                    System.out.printf("El valor de 1 Dólar en Euros es: %.2f%n", tasaCambio.getConversionRates().get("EUR"));
                    break;
                case 6:
                    double valorEnDolaresEUR = 1 / tasaCambio.getConversionRates().get("EUR");
                    System.out.printf("El valor de 1 Euro en Dólares es: %.2f%n", valorEnDolaresEUR);
                    break;
                case 7:
                    System.out.println("Gracias por usar el conversor de monedas.");
                    break;
                default:
                    System.out.println("Opción no válida, digite alguna de las opciones anteriores");
            }
        }
        teclado.close();
    }

    private static TasaDeCambio obtenerTasasDeCambio() {
        String url = "https://v6.exchangerate-api.com/v6/d9e14c46c53ec8238b954efa/latest/USD"; // Cambia la clave según corresponda
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, TasaDeCambio.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener tasas de cambio: " + e.getMessage());
            return null; // Retorna null si hay un error
        }
    }
}
