package autoscout24;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * AutoScout 24 Scraper
 */
public class ScrapingAutoScout {
    public static final int MAX_PAGES = 20;
    private static final String path = "C:\\Users\\matte\\Downloads\\scrapingAutoScoutSupercar.csv";
    private static Boolean filter = false;

    public static void main(String[] args) {
        try {

            // csv file creation
            File csvFile = new File(path);
            CSVWriter csvWrite = new CSVWriter(new FileWriter(csvFile), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            String[] entries = Attributes.ATTRIBUTES.toArray(new String[0]);
            Arrays.sort(entries);
            csvWrite.writeNext(entries);

            Arrays.asList(Brand.BRANDS).forEach(brand -> {
                brand = brand.replaceAll(" ", "-").toLowerCase();
                //String finalBrand = brand;
                //Arrays.asList(Brand.MODELS).forEach(model -> {
                    /*model = model.replaceAll(" ", "-").toLowerCase();

                    if (finalBrand.equals("audi") && (!Objects.equals(model, "a1") && !Objects.equals(model, "a3")))
                        return;
                    else if (finalBrand.equals("bmw") && (!Objects.equals(model, "x1") && !Objects.equals(model, "x3")))
                        return;
                    else if (finalBrand.equals("ford") && (!Objects.equals(model, "fiesta") && !Objects.equals(model, "focus")))
                        return;
                    else if (finalBrand.equals("volkswagen") && (!Objects.equals(model, "golf") && !Objects.equals(model, "polo")))
                        return;
                    else if (finalBrand.equals("citroen") && (!Objects.equals(model, "c3") && !Objects.equals(model, "c4")))
                        return;
                    else if (finalBrand.equals("renault") && (!Objects.equals(model, "captur")))
                        return;
                    else if (finalBrand.equals("fiat") && (!Objects.equals(model, "500")))
                        return;
                    else if (finalBrand.equals("opel") && (!Objects.equals(model, "corsa")))
                        return;
                    else if (finalBrand.equals("toyota") && (!Objects.equals(model, "yaris")))
                        return;
                    else if (finalBrand.equals("volvo") && (!Objects.equals(model, "xc40")))
                        return;*/

                    for (int page = 1; page <= MAX_PAGES; page++) {
                    try {
                        Elements elements = null;
                        if (!filter) {
                            Document d = Jsoup.connect("https://www.autoscout24.com/lst/" + brand +
                                    "?offer=U&sort=standard&desc=0&atype=C&ustate=N%2CU&powertype=kw&search" +
                                    "_id=gxijy4ie8u&page=" + page).userAgent("Mozilla").timeout(3000).get();
                            System.out.println("https://www.autoscout24.com/lst/" + brand +
                                    "?offer=U&sort=standard&desc=0&atype=C&ustate=N%2CU&powertype=kw&search" +
                                    "_id=gxijy4ie8u&page=" + page);
                            d.setBaseUri("https://www.autoscout24.com/lst/" + brand +
                                    "?offer=U&sort=standard&desc=0&atype=C&ustate=N%2CU&powertype=kw&search" +
                                    "_id=gxijy4ie8u&page=" + page);
                            elements = d.select(".ListItem_wrapper__J_a_C");
                        }/*
                        else {
                            Document d = Jsoup.connect("https://www.autoscout24.com/lst/" + brand +
                                    "?fregfrom=1992&fregto=2021&kmfrom=50000&offer=U&sort=standard&desc=0&atype=C&ustate=N%2CU&powertype=kw&search" +
                                    "_id=1yg5othx083&page=" + page).userAgent("Mozilla").timeout(3000).get();
                            System.out.println("https://www.autoscout24.com/lst/" + brand +
                                    "?fregfrom=1992&fregto=2021&kmfrom=50000&offer=U&sort=standard&desc=0&atype=C&ustate=N%2CU&powertype=kw&search" +
                                    "_id=1yg5othx083&page=" + page);
                            d.setBaseUri("https://www.autoscout24.com/lst/" + brand +
                                    "?fregfrom=1992&fregto=2021&kmfrom=50000&offer=U&sort=standard&desc=0&atype=C&ustate=N%2CU&powertype=kw&search" +
                                    "_id=1yg5othx083&page=" + page);
                            elements = d.select(".ListItem_wrapper__J_a_C");
                        }*/

                        if (!elements.isEmpty()) {
                            for (Element element : elements) {

                                // get url of the car
                                String url = element.select("a").first().absUrl("href");

                                try {
                                    Document auto = Jsoup.connect(url).userAgent("Mozilla").timeout(5000).get();

                                    String manufacturer = new String(auto.select(".css-a36h5").text().getBytes(), StandardCharsets.ISO_8859_1);
                                    String model = new String(auto.select(".css-1mhe8d.errr7t00").text().getBytes(), StandardCharsets.ISO_8859_1);
                                    String price = new String(auto.select(".StandardPrice_price__X_zzU").text().split(" ")[1].split(".-")[0]
                                            .replace(",", "").getBytes(), StandardCharsets.US_ASCII);
                                    String country = new String(auto.select(".scr-link.css-4uy6qb").text().split(",")[1]
                                            .getBytes(), StandardCharsets.ISO_8859_1);
                                    String first_registration = new String(auto.select(".VehicleOverview_itemText__V1yKT").get(2).text()
                                            .split("/")[1].getBytes(), StandardCharsets.ISO_8859_1);

                                    Map<String, String> map = new HashMap<String, String>();
                                    for (int i = 0; i < Attributes.ATTRIBUTES.size(); i++) {
                                        String key = Attributes.ATTRIBUTES.get(i);
                                        map.put(key, "");
                                    }

                                    map.put("Manufacturer", manufacturer);
                                    map.put("Model", model);
                                    map.put("Price", price);
                                    map.put("Country", country);
                                    map.put("First registration", first_registration);

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
                                    map = sortbykey(map);

                                    // creazione stringa per scrivere nel csv
                                    Iterator<String> iterator = map.keySet().iterator();
                                    List<String> values = new ArrayList<>();
                                    while (iterator.hasNext()) {
                                        String key = iterator.next();
                                        String occurrence = map.get(key);
                                        values.add(occurrence);
                                    }
                                    String[] valuesToCsv = values.toArray(new String[0]);
                                    csvWrite.writeNext(valuesToCsv);

                                } catch (Exception e1) {
                                    System.out.println("Error for url " + url);
                                    e1.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*if (page == MAX_PAGES && !filter){
                        page = 0;
                        filter = true;
                    } else if (page == MAX_PAGES) {
                        filter = false;
                    }*/
                }
            });
            //});
            // csv
            csvWrite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void iterateData(Elements e, Map<String, String> map) {
        String index = null;
        for (Element element : e) {

            String span = element.tagName();
            if (Objects.equals(span, "span")) {
                String attrName = element.text();
                if (attrName.contains("emissions"))
                    attrName = "CO2 Emissions";
                if (Attributes.ATTRIBUTES.contains(attrName)) {
                    index = new String(attrName.getBytes(), StandardCharsets.ISO_8859_1);
                } else
                    index = null;
            }

            String dd = element.tagName();
            String content = element.text();
            if (Objects.equals(dd, "dd") && index != null) {
                if (!element.children().select("li").isEmpty()) {
                    Elements ul = element.children().select("li");
                    content = "";
                    for (Element li : ul) {
                        content += li.text() + "; ";
                        content = content.replace(",", ";");
                    }
                } else if (index.equals("Empty weight") || index.equals("Engine size") || index.equals("Mileage")) {
                    content = content.replace(",", "");
                }
                map.put(index, new String(content.getBytes(), StandardCharsets.ISO_8859_1));
            }
        }
    }

    public static TreeMap<String, String> sortbykey(Map<String, String> map) {
        // TreeMap to store values of HashMap
        TreeMap<String, String> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }
}
