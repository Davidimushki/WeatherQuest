package company;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class SampleCityApi {

    // Members
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String URL_PARAM = "&lang=he&appid=77bc97d1ecf641701dc2692ce29f0090";

    // Methods

    // Represent reading data from api service
    public static String readDataFromUrl(String cityName) throws IOException {

        try (InputStream is = new URL(BASE_URL + cityName + URL_PARAM).openStream()) {

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAllData(rd);

        }
    }

    // Represents the reading of the information that came from stream
    private static String readAllData(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
