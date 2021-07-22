package company;

import java.util.Date;

public class City {

    // Members
    private int cityId;
    private long sampleTime;
    private String cityName;
    private int temperature;
    private float windSpeed;

    // Properties

    // Return id of city
    public int getCityId() {
        return this.cityId;
    }

    // Set city id
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    // Return date time that this city's sample
    public long getSampleTime() {
        return this.sampleTime;
    }

    // Update sample time of current city
    public void setSampleTime(long sampleTime) {
        this.sampleTime = sampleTime;
    }

    // Return city name
    public String getCityName() {
        return this.cityName;
    }

    // Set city name
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // Return temperature of current city
    public int getTemperature() {
        return this.temperature;
    }

    // Set new temperature
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    // Return wind speed of current city
    public float getWindSpeed() {
        return this.windSpeed;
    }

    // Set wide speed
    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    // Ctor - initial all members
    public City(String cityName) {
        this.sampleTime = 0;
        this.cityId = 0;
        this.cityName = cityName;
        this.temperature = 0;
        this.windSpeed = 0;
    }
}
