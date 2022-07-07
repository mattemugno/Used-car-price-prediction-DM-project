package autoscout24;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * AutoScout 24 Scraper
 */
public class App {
    public static final int MAX_PAGES = 20;

    public static void main(String[] args) {
        try {
            Arrays.asList(Brand.BRANDS).forEach(brand -> {
                // System.out.println(" --------- " + brand + " --------------- ");
                brand = brand.replaceAll(" ", "-").toLowerCase();
                for (int page = 1; page <= MAX_PAGES; page++) {
                    try {
                        Document d = Jsoup.connect("https://www.autoscout24.com/lst/" + brand +
                                "?sort=standard&size=20&desc=0&offer=J%2CU%2CO%2CD%2CS&ustate=N%2CU&cy=I&atype=C&page="
                                + page).userAgent("Mozilla").timeout(3000).get();
                        System.out.println("https://www.autoscout24.com/lst/" + brand +
                                "?sort=standard&size=20&desc=0&offer=J%2CU%2CO%2CD%2CS&ustate=N%2CU&cy=I&atype=C&page="
                                + page);
                        d.setBaseUri("https://www.autoscout24.com/lst/" + brand +
                                "?sort=standard&desc=0&offer=J%2CU%2CO%2CD%2CS&ustate=N%2CU&cy=I&atype=C&page="
                                + page);
                        Elements elements = d.select(".ListItem_wrapper__J_a_C");

                        if (!elements.isEmpty()) {
                            for (Element element : elements) {

                                // get url
                                String url = element.select("a").first().absUrl("href");
                                // get image
                                //System.out.println(immUrl);
                                // create auto
                                org.bson.Document query = new org.bson.Document();
                                query.append("_id", url);

                                try {
                                    Document auto = Jsoup.connect(url).userAgent("Mozilla").timeout(3000).get();

                                    String manufacturer = new String(auto.select(".css-a36h5").text().getBytes(), StandardCharsets.ISO_8859_1);
                                    String model = new String(auto.select(".css-1mhe8d.errr7t00").text().getBytes(), StandardCharsets.ISO_8859_1);
                                    String price = new String(auto.select(".StandardPrice_price__X_zzU").text().split(" ")[1].split(".-")[0]
                                            .getBytes(), StandardCharsets.US_ASCII);

                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("Manufacturer", manufacturer);
                                    map.put("Model", model);
                                    map.put("Price", price);

                                    // dati di base
                                    Elements e = auto.select("[data-cy=\"basic-details-section\"]").select("span,dd,ul");
                                    iterateData(e, map);

                                    // cronologia veicolo
                                    e = auto.select("[data-cy=\"listing-history-section\"]").select("span,dd");
                                    iterateData(e, map);

                                    // dati tecnici
                                    e = auto.select("[data-cy=\"technical-details-section\"]").select("span,dd");
                                    iterateData(e, map);

                                    // ambiente
                                    e = auto.select("[data-cy=\"environment-details-section\"]").select("span,dd");
                                    iterateData(e, map);

                                    // equipaggiamento
                                    e = auto.select("[data-cy=\"equipment-section\"]").select("span,dd");
                                    iterateData(e, map);

                                    // colore e interni
                                    e = auto.select("[data-cy=\"color-section\"]").select("span,dd");
                                    iterateData(e, map);

                                    System.out.println(map.toString());

                                    // create auto

                                            /*org.bson.Document autoDoc = new org.bson.Document();
                                            autoDoc.append("_id", url);
                                            autoDoc.append("title", titolo);
                                            autoDoc.append("descrizione", descrizione);
                                            autoDoc.append("url", url);
                                            autoDoc.append("prezzo", prezzo);
                                            autoDoc.append("prezzoLabel", prezzoLabel);
                                            autoDoc.append("equip", equip);
                                            for (String key : mappa.keySet()) {
                                                autoDoc.append(key, mappa.get(key));
                                            }
                                            autoDoc.append("tipoAuto", tipoAuto);
                                            autoDoc.append("concessionario", concessionario);
                                            autoDoc.append("indirizzo", indirizzo);
                                            autoDoc.append("km", km);
                                            autoDoc.append("imm", imm);
                                            autoDoc.append("cavalli", cavalli);
                                            autoDoc.append("usato", usato);
                                            autoDoc.append("nprop", nprop);
                                            autoDoc.append("cambio", cambio);
                                            autoDoc.append("alimentazione", alimentazione);
                                            autoDoc.append("consumo", consumo);
                                            autoDoc.append("emissioni", emissioni);
                                            autoDoc.append("tipoVenditore", tipoVenditore);

                                            System.out.printf(autoDoc.toString());*/

                                } catch (Exception e1) {
                                    System.out.println("Error for url " + url);
                                    e1.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void iterateData(Elements e, Map<String, String> map) {
        String index = null;
        for (Element element : e) {

            String span = element.tagName();
            if (Objects.equals(span, "span")) {
                index = new String(element.text().getBytes(), StandardCharsets.ISO_8859_1);
            }

            String dd = element.tagName();
            if (Objects.equals(dd, "dd")) {
                if (!element.children().select("li").isEmpty()) {
                    Elements ul = element.children().select("li");
                    String equipment = "";
                    for (Element li : ul) {
                        equipment += li.text() + "; ";
                    }
                    map.put(index, new String(equipment.getBytes(), StandardCharsets.ISO_8859_1));
                } else
                    map.put(index, new String(element.text().getBytes(), StandardCharsets.ISO_8859_1));
            }
        }
    }
}
