import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImDbApiClient {
    private String apiKey;

    public ImDbApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBody(){
        URI uri = URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
}
