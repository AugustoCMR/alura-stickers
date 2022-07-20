import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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

            String stars = "";
            int somaestrelas;

            for(somaestrelas = 0; somaestrelas < Double.valueOf(filme.get("imDbRating")).intValue()/2; somaestrelas++) {
                stars += "\u2B50";
            }
        
                String imageThumb = filme.get("image");
                String urlImagem = imageThumb.replace("_V1_UX128_CR0,3,128,176_AL_", "");
                String nomeFilme = filme.get("title");
                String nomeArquivo = nomeFilme.replace(":", "-") + ".png";

                String texto = stars;

                InputStream inputStream = new URL(urlImagem).openStream();

                var geradora = new GeradoraDeFigurinhas();
                geradora.cria(inputStream, nomeArquivo, texto);

                System.out.println(nomeFilme);
                                                                         
        }   
    }
}
