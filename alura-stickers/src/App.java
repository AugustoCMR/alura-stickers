import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
         // Fazer uma conexão HTTP e buscar os top 250 filmes

        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Pegar os dados que interessam (Titulo, Poster, Classificação)

        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        

        // Exibir dados

        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println( "\u001b[3m \u001b[33m Titulo: \u001b[45m" + filme.get("title") + "\u001b[1m \u001b[m");
            System.out.println("\u001b[33m \u001b[3m Poster: \u001b[m" + "\u001b[1m" + filme.get("image") + "\u001b[m");
            String imdbRating = filme.get("imDbRating");
            Double imdbRatingDouble = Double.parseDouble(imdbRating);
            Long roundedRating = Math.round(imdbRatingDouble);
            System.out.println("\u001b[3m \u001b[33mClassificação:\u001b[m " + "\u001b[0m" + filme.get("imDbRating"));
            for(int i = 0; i < roundedRating; i++) {
                System.out.print("\u2b50");
            }
            System.out.println();
        }
    }
}
