import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;


public class Main {
    public static void main(String[] args)  {

        Properties properties = new Properties();

        // Carregar o arquivo com as properties
        try {
            properties.load(Main.class.getResourceAsStream("./resources/app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-- Top 250 Movies on IMDB --");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb-api.com/en/API/Top250Movies/" + properties.getProperty("api-key")))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}