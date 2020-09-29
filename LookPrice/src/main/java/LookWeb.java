import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LookWeb {

    Map<Integer, String[]> listWebs = new HashMap<>();
    LookPrice lookPrice; //Buscador de precios


    static public void main(String[] args){
        LookWeb lookWeb = new LookWeb(); //Buscador en webs
        lookWeb.initWebs();

        String search = ""; //Variable para guardar el valor a buscar
        int opc;//Variable para guardar la opcion seleccionada

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            lookWeb.menuInit();
            System.out.print("Elige una opcion: ");
            opc = scanner.nextInt();
            if (opc != 0) {
                System.out.print("Ingresa tu busqueda: ");
                search = scanner.next();
            }
            System.out.println("-------------------------------------------------------------------------------------------------------");
            switch (opc){
                case 1:
                    int count = opc + 1;
                    System.out.println("Busqueda en: " +lookWeb.listWebs.get(count++)[1]);
                    lookWeb.searchDigitalife(lookWeb.listWebs.get(2)[2] + search );
                    System.out.println("Busqueda en: " +lookWeb.listWebs.get(count++)[1]);
                    lookWeb.searchCyberpuerta(lookWeb.listWebs.get(3)[2] + search);
                    System.out.println("Busqueda en: " +lookWeb.listWebs.get(count++)[1]);
                    lookWeb.searchZegucom(lookWeb.listWebs.get(4)[2] + search + "&mod=search&reg=1&sa=true");
                    System.out.println("Busqueda en: " +lookWeb.listWebs.get(count++)[1]);
                    lookWeb.searchMercadoLibre(lookWeb.listWebs.get(5)[2] + search + "#D[A:" + search + "]");
                    System.out.println("Busqueda en: " +lookWeb.listWebs.get(count++)[1]);
                    lookWeb.searchAmazonMX(lookWeb.listWebs.get(6)[2] + search);
                    System.out.println("Busqueda en: " +lookWeb.listWebs.get(count++)[1]);
                    lookWeb.searchPcGamingMX(lookWeb.listWebs.get(7)[2] + search);
                    break;
                case 2:
                    lookWeb.searchDigitalife(lookWeb.listWebs.get(opc)[2] + search );
                    break;
                case 3:
                    lookWeb.searchCyberpuerta(lookWeb.listWebs.get(opc)[2] + search);
                    break;
                case 4:
                    lookWeb.searchZegucom(lookWeb.listWebs.get(opc)[2] + search + "&mod=search&reg=1&sa=true");
                    break;
                case 5:
                    lookWeb.searchMercadoLibre(lookWeb.listWebs.get(opc)[2] + search + "#D[A:" + search + "]");
                    break;
                case 6:
                    lookWeb.searchAmazonMX(lookWeb.listWebs.get(opc)[2] + search);
                    break;
                case 7:
                    lookWeb.searchPcGamingMX(lookWeb.listWebs.get(opc)[2] + search);
                    break;
                default:
                    exit = true;
                    break;
            }
        }
        System.out.println("gracias por usar este sistema de busqueda........");
    }
    private void initWebs(){
        listWebs.put(2, new String[]{"Digitalife","www.digitalife.com.mx","https://www.digitalife.com.mx/productos/busqueda/"});
        listWebs.put(3, new String[]{"Cyberpuerta","www.digitalife.com.mx","https://www.cyberpuerta.mx/index.php?cl=search&searchparam="});
        listWebs.put(4, new String[]{"Zegucom","www.zegucom.com.mx","https://www.zegucom.com.mx/index.php?o=re&cons="});
        listWebs.put(5, new String[]{"MercadoLibre","www.mercadolibre.com.mx","https://listado.mercadolibre.com.mx/"});
        listWebs.put(6, new String[]{"Amazon MX","www.amazon.com.mx", "https://www.amazon.com.mx/s?k="});
        listWebs.put(7, new String[]{"PC Gaming MX","https://pcgaming.mx/", "https://pcgaming.mx/shop?&search="});
    }

    public void menuInit (){
        System.out.println("Elija la pagina donde desea realizar su busqueda:");
        System.out.println("1. Buscar en todas");
        int i = 2;
        for (Map.Entry<Integer, String[]> me : listWebs.entrySet()){
            System.out.println(i + ". " + me.getValue()[0] + " (" + me.getValue()[1] + ")");
            i++;
        }
        System.out.println("0. Exit");
    }

    public void searchDigitalife(String urlWeb){
        lookPrice = new LookPrice(urlWeb + "/existencias/1");
        if (lookPrice.getConnection() == 200) {
            Elements elements = lookPrice.getElements(lookPrice.getDocument(), "div.productoInfoBloq");
            for (Element element : elements) {
                System.out.println("Producto: " + element.getElementsByClass("tituloHighlight").text());
                System.out.println("Precio: " + element.select("div.precioGrid2.precioFlag").text());
                System.out.println("Link: " + element.select("a").attr("href"));
                System.out.println("-------------------------------------------------------------------------------------------------------");
            }
            Element paginas = lookPrice.getElement(lookPrice.getDocument(), "div.pagination.pull-right");
            if (!(paginas == null)){
                Elements pagUrl = paginas.select("a");
                if (pagUrl.size()>0) {
                    int i = 1;
                    for (Element url : pagUrl) {
                        lookPrice = new LookPrice(url.attr("href"));
                        if (lookPrice.getConnection() == 200 && i < pagUrl.size()) {
                            System.out.println("Pagina " + (i + 1));
                            elements = lookPrice.getElements(lookPrice.getDocument(), "div.productoInfoBloq");
                            for (Element element : elements) {
                                System.out.println("Producto: " + element.getElementsByClass("tituloHighlight").text());
                                System.out.println("Precio: " + element.select("div.precioGrid2.precioFlag").text());
                                System.out.println("Link: " + element.select("a").attr("href"));
                                System.out.println("-------------------------------------------------------------------------------------------------------");
                            }
                        }
                        i++;
                    }
                }
            }
        }else {
            System.out.println("Pagina no disponible");
        }
    }

    public void searchCyberpuerta(String urlWeb){
        lookPrice = new LookPrice(urlWeb);
        if (lookPrice.getConnection() == 200) {
            Elements elements = lookPrice.getElements(lookPrice.getDocument(), "li.cell productData.small-12.small-order-1");
            for (Element element : elements) {
                System.out.println("Producto: " + element.getElementsByClass("emproduct_right_title emsmoothtext cpGaProdsearchList-1").text());
                System.out.println("Precio: " + element.getElementsByClass("price").first().text());
                System.out.println("Link: " + element.select("a").first().attr("href"));
                System.out.println("-------------------------------------------------------------------------------------------------------");
            }
            Elements paginas = lookPrice.getElements(lookPrice.getDocument(), "a.page");
            if (!(paginas == null)) {
                int i = 1;
                for (Element pagUrl : paginas) {
                    lookPrice = new LookPrice(pagUrl.select("a").first().attr("href"));
                    if (lookPrice.getConnection() == 200 && i > 1 && i < paginas.size()) {
                        System.out.println("Pagina " + i + "=================================================");
                        elements = lookPrice.getElements(lookPrice.getDocument(), "li.cell productData.small-12.small-order-1");
                        for (Element element : elements) {
                            System.out.println("Producto: " + element.getElementsByClass("emproduct_right_title emsmoothtext cpGaProdsearchList-1").text());
                            System.out.println("Precio: " + element.getElementsByClass("price").first().text());
                            System.out.println("Link: " + element.select("a").first().attr("href"));
                            System.out.println("-------------------------------------------------------------------------------------------------------");
                        }
                    }
                    i++;
                }
            }
        }
    }

    public void searchZegucom(String urlWeb){
        lookPrice = new LookPrice(urlWeb);
        if (lookPrice.getConnection() == 200) {
            Elements elements = lookPrice.getElements(lookPrice.getDocument(), "div.search-result");
            for (Element element : elements) {
                System.out.println("Producto: " + element.getElementsByClass("result-description").text());
                System.out.println("Precio: " + element.getElementsByClass("result-price-search").text());
                System.out.println("Link: https://www.zegucom.com.mx" + element.getElementsByClass("result-description").select("a").attr("href"));
                System.out.println("-------------------------------------------------------------------------------------------------------");
            }
        }else {
            System.out.println("Pagina no disponible");
        }
    }

    public void searchMercadoLibre(String urlWeb){
        lookPrice = new LookPrice(urlWeb);
        if (lookPrice.getConnection() == 200) {
            Elements elements = lookPrice.getElements(lookPrice.getDocument(), "li.ui-search-layout__item");
            for (Element element : elements) {
                System.out.println("Producto: " + element.getElementsByClass("ui-search-item__title").text());
                System.out.println("Precio: " + element.getElementsByClass("price-tag ui-search-price__part").first().text());
                System.out.println("Link: " + element.select("a").first().attr("href"));
                System.out.println("-------------------------------------------------------------------------------------------------------");
            }
            Elements paginas = lookPrice.getElements(lookPrice.getDocument(), "li.andes-pagination__button");
            if (!(paginas == null)){
                int i = 1;
                for (Element pagUrl : paginas){
                    lookPrice = new LookPrice(pagUrl.select("a").first().attr("href"));
                    if (lookPrice.getConnection() == 200 && i > 1 && i < paginas.size()){
                        System.out.println("Pagina " + i + "=================================================");
                        elements = lookPrice.getElements(lookPrice.getDocument(), "li.ui-search-layout__item");
                        for (Element element : elements) {
                            System.out.println("Producto: " + element.getElementsByClass("ui-search-item__title").text());
                            System.out.println("Precio: " + element.getElementsByClass("price-tag ui-search-price__part").first().text());
                            System.out.println("Link: " + element.select("a").first().attr("href"));
                            System.out.println("-------------------------------------------------------------------------------------------------------");
                        }
                    }
                    i++;
                }
            }
        }else {
            System.out.println("Pagina no disponible");
        }
    }

    public void searchAmazonMX(String urlWeb){
        lookPrice = new LookPrice(urlWeb);
        if (lookPrice.getConnection() == 200) {
            Elements elements = lookPrice.getElements(lookPrice.getDocument(), "div.s-result-item").not("div.a-section.a-spacing-none.s-result-item.s-flex-full-width.s-widget");
            for (Element element : elements) {
                System.out.println("Producto: " + element.getElementsByClass("a-size-base-plus a-color-base a-text-normal").text());
                if (element.getElementsByClass("a-offscreen").first() != null){
                    System.out.println("Precio: " + element.getElementsByClass("a-offscreen").first().text());
                }else {
                    System.out.println("Precio en el interior del enlace");
                }
                System.out.println("Link: https://www.amazon.com.mx/" + element.select("a").first().attr("href"));
                System.out.println("-------------------------------------------------------------------------------------------------------");
            }
            Elements paginas = lookPrice.getElements(lookPrice.getDocument(), "li.a-normal");
            if (!(paginas == null)){
                int i = 2;
                for (Element pagUrl : paginas){
                    lookPrice = new LookPrice("https://www.amazon.com.mx/" + pagUrl.select("a").first().attr("href"));
                    if (lookPrice.getConnection() == 200){
                        System.out.println("Pagina " + i + "=================================================");
                        elements = lookPrice.getElements(lookPrice.getDocument(), "div.s-result-item").not("div.a-section.a-spacing-none.s-result-item.s-flex-full-width.s-widget");
                        for (Element element : elements) {
                            System.out.println("Producto: " + element.getElementsByClass("a-size-base-plus a-color-base a-text-normal").text());
                            if (element.getElementsByClass("a-offscreen").first() != null){
                                System.out.println("Precio: " + element.getElementsByClass("a-offscreen").first().text());
                            }else {
                                System.out.println("Precio en el interior del enlace");
                            }
                            System.out.println("Link: https://www.amazon.com.mx/" + element.select("a").first().attr("href"));
                            System.out.println("-------------------------------------------------------------------------------------------------------");
                        }
                    }
                    i++;
                }
            }
        }else {
            System.out.println("Pagina no disponible");
        }
    }

    public void searchPcGamingMX(String urlWeb){
        lookPrice = new LookPrice(urlWeb);
        if (lookPrice.getConnection() == 200) {
            Elements elements = lookPrice.getElements(lookPrice.getDocument(), "td.oe_product ");
            for (Element element : elements) {
                System.out.println("Producto: " + element.getElementsByClass("o_wsale_products_item_title").text());
                System.out.println("Precio: " + element.getElementsByAttributeValue("itemprop","price").text());
                System.out.println("Link: https://pcgaming.mx/" + element.getElementsByClass("o_wsale_products_item_title").select("a").first().attr("href"));
                System.out.println("-------------------------------------------------------------------------------------------------------");
            }
            Elements paginas = lookPrice.getElements(lookPrice.getDocument(), "li.page-item").not("li.page-item.active").not("li.page-item.disabled");
            if (!(paginas == null)){
                int i = 2;
                for (Element pagUrl : paginas){
                    lookPrice = new LookPrice("https://pcgaming.mx/" + pagUrl.select("a").first().attr("href"));
                    if (lookPrice.getConnection() == 200){
                        System.out.println("Pagina " + i + "=================================================");
                        elements = lookPrice.getElements(lookPrice.getDocument(), "td.oe_product ");
                        for (Element element : elements) {
                            System.out.println("Producto: " + element.getElementsByClass("o_wsale_products_item_title").text());
                            System.out.println("Precio: " + element.getElementsByAttributeValue("itemprop","price").text());
                            System.out.println("Link: https://pcgaming.mx/" + element.getElementsByClass("o_wsale_products_item_title").select("a").first().attr("href"));
                            System.out.println("-------------------------------------------------------------------------------------------------------");
                        }
                    }
                    i++;
                }
            }
        }else {
            System.out.println("Pagina no disponible");
        }
    }
}
