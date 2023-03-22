package fr.diginamic.entites;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import java.util.ArrayList;
import java.util.List;

public class MovieService {

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
    public static List<Movie> parseMovies(HtmlPage page){

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
