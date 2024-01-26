package VladMaltsev.weatherapp.util.ImageGeneration;

import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.IOException;
import java.util.List;

public class GetImage {
    private GetImage(){}

    public static byte[] createGraphicsHours(List<WeatherDuringDay> weatherDuringDayList, String country, String city) throws IOException {

        double[] hour = new double[24];
        double[] tempPerHour = new double[24];
        double[] windPerHour = new double[24];
        double[] humPerHour = new double[24];
        int i = 0;
        for (WeatherDuringDay w : weatherDuringDayList) {
            hour[i] = i;
            tempPerHour[i] = w.getTemp();
            windPerHour[i] = w.getWind() * 5;
            humPerHour[i] = w.getHum();
            i++;
        }
        return getBytes(country, city, hour, tempPerHour, windPerHour, humPerHour, "Hour");
    }

    public static byte[] createGraphicsDays(List<WeatherDaySnapshot> weatherDaySnapshotList, String country, String city) throws IOException {
        double[] day = new double[15];
        double[] tempPerDay = new double[15];
        double[] windPerDay = new double[15];
        double[] humPerDay = new double[15];
        int i = 0;

        for (WeatherDaySnapshot w : weatherDaySnapshotList) {
            day[i] = i;
            tempPerDay[i] = w.getAverageTemperature();
            windPerDay[i] = w.getAverageWindSpeed() * 5;
            humPerDay[i] = w.getAverageHumidity();
            i++;
        }
        return getBytes(country, city, day, tempPerDay, windPerDay, humPerDay, "Days");
    }

    private static byte[] getBytes(String country, String city, double[] dXStep, double[] temperature, double[] windSpeed, double[] humidity, String interval) throws IOException {
        XYChart chart = new XYChartBuilder().
                width(800).height(600).
                title(country + ", " + city).
                xAxisTitle(interval).yAxisTitle("Amount").build();

        chart.addSeries("Temperature, F", dXStep, temperature);
        chart.addSeries("Windspeed*5, mi/h", dXStep, windSpeed);
        chart.addSeries("Humidity, %", dXStep, humidity);

        return BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.JPG);
    }
}
