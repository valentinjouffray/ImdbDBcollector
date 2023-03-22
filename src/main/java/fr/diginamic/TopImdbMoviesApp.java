package fr.diginamic;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import fr.diginamic.entites.Movie;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TopImdbMoviesApp {
    public static void main(String[] args) throws FailingHttpStatusCodeException, IOException {
        WebClient webClient = new WebClient();

        disableCSSandJSsupport(webClient);

        Path pathCible = Paths.get("C:\\Users\\vajou\\OneDrive\\" +
                "Documents\\Diginamic\\untitled\\output\\imdbTop250.csv");

        String url = "https://www.imdb.com/chart/top/?ref_=nv_mv_250"; //URL de la page du top 250 de IMDB
        HtmlPage pageTopMovies = webClient.getPage(url);

        List<Movie> movies = parseMovies(pageTopMovies);

        List<String> csvData = new ArrayList<>();
        csvData.add("Position" + ";" + "Titre" + ";" + "Date de sortie" + ";" + "Note"); //en tête du document CSV

        for (Movie movie : movies) {
            csvData.add(movie.formatToCSVLine());
        }

        Files.write(pathCible,csvData, StandardCharsets.UTF_8);
    }

    /**
     * <p>Désactive les fonctions JS et CSS pour éviter les warnings</p>
     * @param webClient Le Web Client
     */
    private static void disableCSSandJSsupport(WebClient webClient) {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    /**
     * <p>
     * La page contient un tableau, mais il faut en extraire les éléments afin d'en faire des instances
     * </p>
     *
     * <p>
     * La méthode statique a pour but de prendre les éléments de la page, les parser avec des regex
     * et de retourner une liste d'instances de films
     * </p>
     *
     * @param   page    La page de type HtmlPage pour extraire les éléments du tableau
     * @return          Une ArrayList qui contient les instances de films
     */
    private static List<Movie> parseMovies(HtmlPage page){

        HtmlTable table = (HtmlTable) page.getByXPath("//table").get(0);
        HtmlTableBody body = table.getBodies().get(0);
        List<Movie> movies = new ArrayList<>();
        List<HtmlTableRow> rows = body.getRows();

        for (HtmlTableRow row : rows) {
            /*
            On fait une séparation en 2 des éléments de la cellule et on récupère la partie avant qui sera le rang
            et le reste sera ensuite séparé pour avoir le titre et la date de sortie. '.' et '(' étant les délimiteurs.
             */
            String[] rangEtReste = row.getCell(1).asNormalizedText().split("\\.\\s+", 2);
            String[] titreAvecDate = rangEtReste[1].split("\\s*\\(\\s*", 2);

            int rank = Integer.parseInt(rangEtReste[0]);
            String movieName = titreAvecDate[0];
            int yearOfRelease = Integer.parseInt(titreAvecDate[1].replaceAll("\\)", ""));
            float rating = Float.parseFloat(row.getCell(2).asNormalizedText());

            movies.add(new Movie(rank,movieName,yearOfRelease,rating));
        }
        return movies;
    }
}
