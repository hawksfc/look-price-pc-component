import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LookPrice {

    private String url;

    public LookPrice(String url) {
        this.url = url;
    }

    public int getConnection (){
        Response response = null;
        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
            return 0;
        }
        return response.statusCode();
    }

    public Elements getElements(Document doc, String css){
        Elements elements = doc.select(css);
        return elements;
    }

    public Element getElement(Document doc, String css){
        Element element = doc.select(css).first();
        return element;
    }

    public Document getDocument(){
        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
