package company;

public class Main {

    public static void main(String[] args){

        // Represent initial array list with sampleCity object
        SampleCityReadConfig.ExtractCityConfig();

        if (SampleCityReadConfig.getCitiesList() != null){

            System.out.println("Start running....");

            // Run Samples cities for every citi in array list
            for (SampleCity item : SampleCityReadConfig.getCitiesList()){
                item.RunSample();
            }
        }
    }
}
