package divisas;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;


class Cambio {
    String result;
    String documentation;
    String terms_of_use;
    String base_code;
    Map<String, Double> conversion_rates;

    public String getBase() {
        return base_code;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }
}

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Digite la moneda a la que quiere convertir (ej. EUR, GBP, etc.)");
        String conversion = lectura.nextLine().toUpperCase();

        String url = "https://v6.exchangerate-api.com/v6/d9e14c46c53ec8238b954efa/latest/USD";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Cambio divisa = gson.fromJson(json, Cambio.class);

        if (divisa.getConversionRates() != null) {
            System.out.println("Base: " + divisa.getBase());
            for (Map.Entry<String, Double> currency : divisa.getConversionRates().entrySet()) {
                System.out.println(currency.getKey() + ": " + currency.getValue());
            }
        } else {
            System.out.println("No se encontraron divisas.");
        }
    }
}
