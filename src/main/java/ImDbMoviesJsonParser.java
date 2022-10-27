import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImDbMoviesJsonParser {
    private String json;

    public ImDbMoviesJsonParser(String json){
        this.json = json;
    }

    public List<Movie> parse(){
        Gson gson = new Gson();

        List<Movie> movies = new ArrayList<>();

        for (String JSONMovie : getJSONMoviesList(json)) {
            Movie movie = gson.fromJson(JSONMovie, Movie.class);
            movies.add(movie);
        }

        return movies;
    }

    private List<String> getJSONMoviesList(String json){
        int beginIndex = json.indexOf("[") + 1;
        int endIndex = json.indexOf("]");
        return Arrays.asList(json.substring(beginIndex, endIndex).split("(?<=}),"));
    }
}
