package szczech.anna.myplaneticket;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class PlaneTicket {

    public static void main(String[] args) throws SQLException {
        String[][] fromWhereToWhere = destination();
        try (Connection connectionWithDB = DatabaseStuff.connectWithDB()) {
            for (String[] direction : fromWhereToWhere) {
                String fromWhere = direction[0];
                String where = direction[1];
                for (VariableInDB rowFromList : findConnection(fromWhere, where)) {
                    DatabaseStuff.addToDB(connectionWithDB, rowFromList);
                }
            }

        }
    }

    private static String[][] destination() {
        return new String[][]{{"Warszawa", "Teneryfa"}, {"Katowice", "Lizbona"}, {"Warszawa", "Rodos"}};
    }

    public static ArrayList<VariableInDB> findConnection(String fromWhere, String toWhere) {
        ArrayList<VariableInDB> list = new ArrayList<>();
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");               // przegladarka dziala w tle, nie widac jej
        ChromeDriver d = new ChromeDriver(chromeOptions);
        try {
            d.get("https://biletyczarterowe.r.pl/");
            d.findElementById("WylotZ").click();
            String from = d.findElementByCssSelector("#listaLokalizacjiDlaWylotuZ [for^=" + fromWhere + "]").getText();
            d.findElementByCssSelector("#listaLokalizacjiDlaWylotuZ [for^=" + fromWhere + "]").click();
            d.findElementById("WylotDo").click();
            String where = d.findElementByCssSelector("#listaLokalizacjiDlaWylotuDo [for^=" + toWhere + "]").getText();
            d.findElementByCssSelector("#listaLokalizacjiDlaWylotuDo [for^=" + toWhere + "]").click();
            d.findElementById("ButtonSzukajCzarterow").click();

            VariableInDB connection;
            List<WebElement> webElementsPrice = d.findElementsByClassName("cenaNaKalendarzu");
            for (WebElement webElementPrice : webElementsPrice) {
                String time = webElementPrice.findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.className("dataWybranegoDnia")).getAttribute("value");
                connection = new VariableInDB(from, where, time, webElementPrice.getText());
                list.add(connection);
            }
            for (VariableInDB a : list) {
                System.out.println(a.getFromWhere() + " " + a.getWhere() + " " + a.getDate() + " " + a.getPrice());
            }
        } finally {
            d.quit();
        }
        return list;
    }
}
