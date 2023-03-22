package fr.diginamic;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.diginamic.entites.FileWriter;

import java.io.IOException;

public class TopImdbMoviesApp {
    public static void main(String[] args) throws FailingHttpStatusCodeException, IOException {
        WebClient webClient = new WebClient();

        disableCSSandJSsupport(webClient);

        String url = "https://www.imdb.com/chart/top/?ref_=nv_mv_250"; //URL de la page du top 250 de IMDB
        HtmlPage pageTopMovies = webClient.getPage(url);
        FileWriter fw = new FileWriter(pageTopMovies);
        fw.createNewFile();
    }

    /**
     * <p>Désactive les fonctions JS et CSS pour éviter les warnings</p>
     * @param webClient Le Web Client
     */
    private static void disableCSSandJSsupport(WebClient webClient) {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

}
