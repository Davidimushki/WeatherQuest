package company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class SampleCityReadConfig {

    // Members
    private static String data = "";
    private static final String CONFIG_FILE_NAME = "C:/SampleCityConfig/SampleCityConfig.txt";
    private static ArrayList<SampleCity> citiesList;

    // Properties
    public static ArrayList<SampleCity> getCitiesList() {
        return citiesList;
    }

    // Represent extract data info from configuration file to build sampleCity objects to collect data info
    public static void ExtractCityConfig() {

        try {
            File configFile = new File(CONFIG_FILE_NAME);

            if (configFile.exists()) {

                // Collect Data from config file
                Scanner myReader = new Scanner(configFile);
                while (myReader.hasNextLine()) {
                    data += myReader.nextLine();
                }

                JSONObject jsonObj = new JSONObject(data);
                JSONArray arr = jsonObj.getJSONArray("sampleCities");

                // Create SampleCity list
                citiesList = new ArrayList<SampleCity>();

                // Extract Data information about Sample city
                for (int i = 0; i < arr.length(); i++) {
                    citiesList.add(new SampleCity(arr.getJSONObject(i).getString("city_name"),
                                                  arr.getJSONObject(i).getInt("interval_minutes"),
                                                  arr.getJSONObject(i).getInt("alert_threshold_pct")));
                }

            }else
                System.out.println("Configuration file not exists...");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (JSONException e) {
            System.out.println(e.getMessage());

        }
    }
}
