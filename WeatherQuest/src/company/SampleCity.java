package company;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Represent Management of sample weather city
public class SampleCity {

    // **********
    // Members
    // **********
    private static final double CONVERT_KELVIN_CELSIUS_NUM = 273;
    private City currentCity;
    private int intervalTime;
    private int alertThreshold;
    private ScheduledExecutorService executor;
    private Runnable threadRunnable;

    // **********
    // Properties
    // **********

    // Return city object
    public City getCurrentCity() {
        return currentCity;
    }

    // Set / Update city property
    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    // Return Interval time to current sample
    public int getIntervalTime() {
        return intervalTime;
    }

    // Set interval time to current sample
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    // Return threshold number
    public int getAlertThreshold() {
        return alertThreshold;
    }

    // Set threshold number
    public void setAlertThreshold(int alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    // **********
    // Ctor
    // **********

    public SampleCity(String sampleCityName, int intervalTime, int alertThreshold) {
        this.currentCity = new City(sampleCityName);
        this.intervalTime = intervalTime;
        this.alertThreshold = alertThreshold;
    }

    // **********
    // Methods
    // **********

    // Represent start to run sample of current city,
    // create a new thread by given interval to sample
    public void RunSample(){

        // Create single-threaded executor that can schedule commands to run after a given delay, or to execute periodically
        this.executor = Executors.newSingleThreadScheduledExecutor();

        // Create a thread to run method
        this.threadRunnable = new Runnable() {
            @Override
            public void run() {
                StartSample();
            }
        };

        // Run / execute thread by delay time
        this.executor.scheduleAtFixedRate(threadRunnable, 0, this.intervalTime, TimeUnit.MINUTES);
    }

    // Represent start running to collect sample city's weather
    // Create a request to API service to get info about weather
    // Calculate percent threshold
    // Save data collection in CSV file
    private void StartSample() {

        try{

            // Call API service to get information about city
            JSONObject JsonObj = new JSONObject(SampleCityApi.readDataFromUrl(this.getCurrentCity().getCityName()));

            // Check if first time collect sample city
            if(this.getCurrentCity().getTemperature() == 0){

                // initials properties
                this.ExtractSetJsonFields(JsonObj);

                // Create file header
                SampleCityToCSV.createHeaderFile("C:/SampleCityFiles/", this.getCurrentCity().getCityName().replace(" ", "_") + "_" +
                        String.valueOf(this.getCurrentCity().getCityId()) + ".csv");
            }
            else if (this.getCurrentCity().getTemperature() != this.ExtractCityCelsiusTemperature(JsonObj)){

                // Calculate present
                int reductionRate = CalculatePercentTemperature(this.getCurrentCity().getTemperature(), this.ExtractCityCelsiusTemperature(JsonObj));

                if (reductionRate >= this.alertThreshold){

                    // Alert when deference percent more than threshold
                    AlertMsg(this.getCurrentCity().getTemperature(), this.ExtractCityCelsiusTemperature(JsonObj), reductionRate);
                }
            }

            // Update current city property with new information
            this.ExtractSetJsonFields(JsonObj);

            // Call method that save sample information into specific current sample city file
            SampleCityToCSV.saveSampleCityInfo("C:/SampleCityFiles/" + this.getCurrentCity().getCityName().replace(" ", "_") + "_" +
                    String.valueOf(this.getCurrentCity().getCityId()) + ".csv", this.getCurrentCity());

        }catch (IOException | JSONException | ParseException ex){
            System.out.println(ex.getMessage());
        }
    }

    // Representation alert when deference percent more than threshold
    // oldtemp - the previous value of temperature
    // newTemp - the current value of temperature from sample city
    // deference - the deference between old and current values
    private void AlertMsg(int oldTemp, int newTemp, int deference){

        System.out.println("Alert city: " +
                this.getCurrentCity().getCityName() +
                " - percent more than threshold " + this.alertThreshold + "%" +
                ", the previous temperature was : " + oldTemp +
                " and current temperature is : " + newTemp +
                ", the difference is : " +
                deference + "%");
    }

    // Calculate deference percent between temperatures
    private int CalculatePercentTemperature(int oldTemp, int newTemp){

        // Calculate deference present
        return (Math.abs(newTemp - oldTemp) * 100) / oldTemp;
    }

    // Represent info about city temperature
    // Return the temperature, if catch exception return -1
    private int ExtractCityCelsiusTemperature(JSONObject jsonInfo){

        try{

            double temperature = (int) (jsonInfo.getJSONObject("main").getDouble("temp"));
            return  (int) Math.abs(temperature - CONVERT_KELVIN_CELSIUS_NUM);

        }catch (JSONException ex){
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    // Represent info about city wind speed
    // Return the speed, if catch exception return -1
    private float ExtractCityWindSpeed(JSONObject jsonInfo){

        try{
            return  (float) jsonInfo.getJSONObject("wind").getDouble("speed");

        }catch (JSONException ex){
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    // Represent update city's properties
    private void ExtractSetJsonFields(JSONObject jsonInfo){

        try{

            this.getCurrentCity().setCityId(jsonInfo.getInt("id"));
            this.getCurrentCity().setSampleTime(System.currentTimeMillis());
            this.getCurrentCity().setTemperature(this.ExtractCityCelsiusTemperature(jsonInfo));
            this.getCurrentCity().setWindSpeed(this.ExtractCityWindSpeed(jsonInfo));

        }catch (JSONException ex){
            System.out.println(ex.getMessage());
        }
    }
}
