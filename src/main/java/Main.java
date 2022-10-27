import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;


public class Main {
    public static void main(String[] args)  {

        Properties properties = new Properties();

        // Load the file with the properties
        try {
            properties.load(Main.class.getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String json = new ImDbApiClient(properties.getProperty("api-key")).getBody();

        List<Movie> movies = new ImDbMoviesJsonParser(json).parse();

        // Generate HTML
        PrintWriter writer;
        try {
            writer = new PrintWriter("content.html");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        new HTMLGenerator(writer).generate(movies);

        writer.close();
    }
}