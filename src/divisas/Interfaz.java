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
    private Rates rates;

    public String getBase() {
        return base;
    }

    public Rates getRates() {
        return rates;
    }

    class Rates {
        private double COP;
        private double BRL;
        private double EUR;

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
                3- Dólar           => Real brasileño
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
                    System.out.print("Ingrese la cantidad en Dólares a convertir: ");
                    double dolaresCOP = teclado.nextDouble();
                    double resultadoCOP = dolaresCOP * tasaCambio.getConversionRates().get("COP");
                    System.out.printf("El valor de %.2f Dólares en Pesos colombianos es: %.2f%n", dolaresCOP, resultadoCOP);
                    break;
                case 2:
                    System.out.print("Ingrese la cantidad en Pesos colombianos a convertir: ");
                    double pesosDolares = teclado.nextDouble();
                    double resultadoDolares = pesosDolares / tasaCambio.getConversionRates().get("COP");
                    System.out.printf("El valor de %.2f Pesos colombianos en Dólares es: %.2f%n", pesosDolares, resultadoDolares);
                    break;
                case 3:
                    System.out.print("Ingrese la cantidad en Dólares a convertir: ");
                    double dolaresBRL = teclado.nextDouble();
                    double resultadoBRL = dolaresBRL * tasaCambio.getConversionRates().get("BRL");
                    System.out.printf("El valor de %.2f Dólares en Reales brasileños es: %.2f%n", dolaresBRL, resultadoBRL);
                    break;
                case 4:
                    System.out.print("Ingrese la cantidad en Reales brasileños a convertir: ");
                    double realesDolares = teclado.nextDouble();
                    double resultadoDolaresBRL = realesDolares / tasaCambio.getConversionRates().get("BRL");
                    System.out.printf("El valor de %.2f Reales brasileños en Dólares es: %.2f%n", realesDolares, resultadoDolaresBRL);
                    break;
                case 5:
                    System.out.print("Ingrese la cantidad en Dólares a convertir: ");
                    double dolaresEUR = teclado.nextDouble();
                    double resultadoEUR = dolaresEUR * tasaCambio.getConversionRates().get("EUR");
                    System.out.printf("El valor de %.2f Dólares en Euros es: %.2f%n", dolaresEUR, resultadoEUR);
                    break;
                case 6:
                    System.out.print("Ingrese la cantidad en Euros a convertir: ");
                    double eurosDolares = teclado.nextDouble();
                    double resultadoDolaresEUR = eurosDolares / tasaCambio.getConversionRates().get("EUR");
                    System.out.printf("El valor de %.2f Euros en Dólares es: %.2f%n", eurosDolares, resultadoDolaresEUR);
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
        String url = "https://v6.exchangerate-api.com/v6/d9e14c46c53ec8238b954efa/latest/USD";
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
            return null;
        }
    }
}
