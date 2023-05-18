import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
public class WebAPI {
    public static void getNowPlaying() {
        String APIkey = "bf154784a5550a7266ec626d3992f08c"; // your personal API key on TheMovieDatabase
        String queryParameters = "?api_key=" + APIkey;
        String endpoint = "https://api.themoviedb.org/3/movie/now_playing";
        String url = endpoint + queryParameters;
        String urlResponse = "";
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // when determining HOW to parse the returned JSON data,
        // first, print out the urlResponse, then copy/paste the output
        // into the online JSON parser: https://jsonformatter.org/json-parser
        // use the visual model to help you determine how to parse the data!
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONArray movieList = jsonObj.getJSONArray("results");
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject movieObj = movieList.getJSONObject(i);
            String movieTitle = movieObj.getString("title");
            int movieID = movieObj.getInt("id");
            String posterPath = movieObj.getString("poster_path");
            String fullPosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
            System.out.println(movieID + " " + movieTitle + " " + fullPosterPath);
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter a movie ID to learn more: ");
        int movie_id = scan.nextInt();
        String endpoint2 = "https://api.themoviedb.org/3/movie/" + movie_id;
        url = endpoint2 + queryParameters;
        urlResponse = "";
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JSONObject jsonObject2 = new JSONObject(urlResponse);
        String title = jsonObject2.getString("title");
        String homepage = jsonObject2.getString("homepage");
        String overview = jsonObject2.getString("overview");
        String release_date = jsonObject2.getString("release_date");
        int runtime = jsonObject2.getInt("runtime");
        int revenue = jsonObject2.getInt("revenue");
        System.out.println("Title: " + title);
        System.out.println("Homepage: " + homepage);
        System.out.println("Overview: " + overview);
        System.out.println("Release Date: " + release_date);
        System.out.println("Runtime: " + runtime + " minutes");
        System.out.println("Revenue: $" + revenue);
        JSONArray genres = jsonObject2.getJSONArray("genres");
        System.out.println("Genres: ");
        for(int i =0; i < genres.length(); i ++){
            JSONObject genre = genres.getJSONObject(i);
            String genreType = genre.getString("name");
            System.out.println(genreType);
        }
    }

}
