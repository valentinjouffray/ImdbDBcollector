package fr.diginamic.entites;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.entites.MovieService.parseMovies;

public class FileWriter {
    private final Path path = Paths.get("C:\\Users\\vajou\\OneDrive\\" +
            "Documents\\Diginamic\\untitled\\output\\imdbTop250.csv");
    private HtmlPage page;

    /**
     * <p>Constructeur de FileWriter qui instance l'objet avec la page où se trouve le tableau à parcourir</p>
     *
     * @param page Page contenant le tableau
     */
    public FileWriter(HtmlPage page) {
        this.page = page;
    }

    public Path getPath() {
        return path;
    }

    /**
     * <p>La méthode crée un nouveau fichier contenant les données présentes dans la page</p>
     *
     * @throws IOException Exception qui gère l'écriture dans Files.write
     */
    public void createNewFile() throws IOException {
        List<Movie> movies = parseMovies(page);

        List<String> csvData = new ArrayList<>();
        csvData.add("Position" + ";" + "Titre" + ";" + "Date de sortie" + ";" + "Note"); //en tête du document CSV

        for (Movie movie : movies) {
            csvData.add(movie.formatToCSVLine());
        }

        Files.write(path,csvData, StandardCharsets.UTF_8);
    }

}
