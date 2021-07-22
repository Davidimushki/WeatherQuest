package company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

// Management to save monitors towns to CSV file
public class SampleCityToCSV {

    // Represent append file with new row of sample city
    public static void saveSampleCityInfo(String filePath, City city) throws IOException, ParseException {

        File fileTemp = new File(filePath);

        // Check file exist to append
        if (fileTemp.isFile() && fileTemp.exists()){

            FileWriter fWriter = new FileWriter(fileTemp, true);
            fWriter.append(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(city.getSampleTime()) + ",");
            fWriter.append(city.getCityName() + ",");
            fWriter.append(city.getTemperature() + ",");
            fWriter.append(city.getWindSpeed() + "");
            fWriter.append("\n");

            fWriter.flush();
            fWriter.close();
        }
    }

    // Represent create a new file with header
    public static void createHeaderFile(String filePath, String fileName) throws IOException {

        File dirTempObj = new File(filePath);
        File fileTempObj = new File(filePath + fileName);

        dirTempObj.mkdir();

        if (!fileTempObj.exists()){
            fileTempObj.createNewFile();
        }

        // Check file not exist to create new file with header
        FileWriter fWriter = new FileWriter(fileTempObj);
        fWriter.append("Simple time");
        fWriter.append(",City name");
        fWriter.append(",Temperature");
        fWriter.append(",Wind speed");
        fWriter.append("\n");

        fWriter.flush();
        fWriter.close();

    }
}
