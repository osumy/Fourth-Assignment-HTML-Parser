import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Parser {
    static List<Country> countries = new ArrayList<>();

    public static List<Country> sortByName(){
        List<Country> sortedByName = new ArrayList<>(countries);
        sortedByName.sort(Comparator.comparing(Country::getName));
        return sortedByName;
    }

    public static List<Country> sortByCapital(){
        List<Country> sortedByCapital = new ArrayList<>(countries);
        sortedByCapital.sort(Comparator.comparing(Country::getCapital));
        return sortedByCapital;
    }

    private static void bubbleSortPop(List<Country> countries, int n)
    {
        int i, j;
        Country temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (countries.get(j).getPopulation() < countries.get(j+1).getPopulation()) {
                    temp = countries.get(j);
                    countries.set(j, countries.get(j+1));
                    countries.set(j+1, temp);
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
        }
    }

    private static void bubbleSortArea(List<Country> countries, int n)
    {
        int i, j;
        Country temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (countries.get(j).getArea() < countries.get(j+1).getArea()) {
                    temp = countries.get(j);
                    countries.set(j, countries.get(j+1));
                    countries.set(j+1, temp);
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
        }
    }

    public static List<Country> sortByPopulation(){
        List<Country> sortedByPopulation = new ArrayList<>(countries);
        bubbleSortPop(sortedByPopulation, sortedByPopulation.size());
        return sortedByPopulation;
    }

    public static List<Country> sortByArea(){
        List<Country> sortedByArea = new ArrayList<>(countries);
        bubbleSortArea(sortedByArea, sortedByArea.size());
        return sortedByArea;
    }

    public static void setUp() throws IOException, URISyntaxException {
        File file = new File("src/Resources/country-list.html");
        Document htmlDoc = Jsoup.parse(file, "UTF-8");
        Elements elements = htmlDoc.getElementsByClass("country");
        for (Element element : elements){
            Element cI = element.getElementsByClass("country-info").first();
            countries.add(new Country(element.selectFirst("h3.country-name").text(), cI.selectFirst("span.country-capital").text(), Integer.parseInt(cI.selectFirst("span.country-population").text()), Double.parseDouble(cI.selectFirst("span.country-area").text())));
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        setUp();
        while (true) {
            System.out.println("HTML Parser");
            System.out.println("1. Sort By Name");
            System.out.println("2. Sort By Capital");
            System.out.println("3. Sort By Population");
            System.out.println("4. Sort By Area");
            System.out.println("5. Exit");
            System.out.print("\n>> Enter your Option: ");
            Scanner sc = new Scanner(System.in);
            int ch = sc.nextInt();
            if (ch == 1) {
                countries = sortByName();
            }
            else if (ch == 2) {
                countries = sortByCapital();
            }
            else if (ch == 3) {
                countries = sortByPopulation();
            }
            else if (ch == 4) {
                countries = sortByArea();
            }
            else if (ch == 5) {
                break;
            }
            for (Country country : countries){ System.out.println(country); }
        }
    }
}
