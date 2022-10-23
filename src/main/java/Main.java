import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.google.gson.*;


public class Main {
    public static void main(String[] args)  {

        Properties properties = new Properties();

        // Load the file with the properties
        try {
            properties.load(Main.class.getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        URI apiURI = URI.create("https://imdb-api.com/en/API/Top250Movies/" + properties.getProperty("api-key"));

        String json = getJson(apiURI);

        Gson gson = new Gson();

        for (String JSONMovie : getJSONMoviesList(json)) {
            Movie movie = gson.fromJson(JSONMovie, Movie.class);

            System.out.println(movie.getTitle());
            System.out.println(movie.getImage());
            System.out.println(movie.getImDbRating());
            System.out.println(movie.getYear() + "\n");
        }

    }

    public static String getJson(URI uri){

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

    public static List<String> getJSONMoviesList(String json){
        int beginIndex = json.indexOf("[") + 1;
        int endIndex = json.indexOf("]");
        return Arrays.asList(json.substring(beginIndex, endIndex).split("(?<=}),"));
    }

}