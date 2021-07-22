Hi,

This software provides the ability to monitor towns weather around the world.
The software is based on several key components:

this system can be run only windows os.

#Configuration file (named: "SampleCityConfig.txt"), This file tells the system:
which towns to monitors
indicate to sample city weather in all times with interval (in minutes)
compare threshold of deference temperatures - popup alerts

this configuration file should be in path : "C:\SampleCityConfig\SampleCityConfig.txt"
the content is json format - Possibility to add towns in a fixed format like:
     {
         "city_name": "Tel Aviv",
         "interval_minutes": 3,
         "alert_threshold_pct": 20
     }


#Monitor files about towns:
these files document each sample of city:
sample time value
temperature value
wind value
city name

these file are created automatically by inserting a new object city in "SampleCityConfig.txt" file
files are created in path : "C:\SampleCityFiles"


#this system used library :
opencsv-5.5.1.jar
java-json.jar


