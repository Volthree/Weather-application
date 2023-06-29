package VladMaltsev.weatherapp.controller;

import VladMaltsev.weatherapp.Servise.WeatherDaySnapshotService;
import VladMaltsev.weatherapp.Servise.WeatherDuringDayService;
import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import VladMaltsev.weatherapp.models.WeatherDuringDay;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.*;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/image")
public class WeatherRESTController {

    private final WeatherDaySnapshotService weatherDaySnapshotService;
    private final WeatherDuringDayService weatherDuringDayService;

    public WeatherRESTController(WeatherDaySnapshotService weatherDaySnapshotService, WeatherDuringDayService weatherDuringDayService) {
        this.weatherDaySnapshotService = weatherDaySnapshotService;
        this.weatherDuringDayService = weatherDuringDayService;
    }

    @GetMapping(value = "/getsingleday",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getSingleDay(@RequestParam(name = "city") String city,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "date") LocalDate date) throws IOException {

        String data = getDataFromPage(city, country, date);

        List<WeatherDaySnapshot> weatherDaySnapshot = parseData(data);
        weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshot);

        List<WeatherDuringDay> w = parseDataDuringDay(data, weatherDaySnapshot, 0);
        weatherDuringDayService.addListDuringDay(w);

        return createGraphicsHours(w, country, city);
    }

    @GetMapping(value = "/getlastdays",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getLastTwoWeeks(@RequestParam(name = "city") String city,
                                  @RequestParam(name = "country") String country) throws IOException {

        String data = getDataFromPage(city, country, null);

        List<WeatherDaySnapshot> weatherDaySnapshot = parseData(data);
        weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshot);

        return createGraphicsDays(weatherDaySnapshot, country, city);
    }


    private String getDataFromPage(String town, String country, LocalDate data) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url;
        if (data == null) {
            url = "https://weather.visualcrossing.com/" +
                    "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country +
                    "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
        } else {
            url = "https://weather.visualcrossing.com/" +
                    "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country + "/" + data +
                    "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
        }
        /////Read local data//////
//        byte[] b = Files.readAllBytes(Paths.get("E:\\All Java\\weatherapp\\src\\main\\resources\\static\\JSONData.json"));
//        String resJSON = new String(b);
        //////////////

        return restTemplate.getForObject(url, String.class);
    }

    private List<WeatherDaySnapshot> parseData(String data) throws JsonProcessingException {

        JsonNode jsonNode = getJsonNode(data);

        List<WeatherDaySnapshot> weatherDaySnapshotList = new LinkedList<>();
        int i = 0;
        while (jsonNode.get("days").get(i) != null) {
            WeatherDaySnapshot weatherDaySnapshot = new WeatherDaySnapshot();
            weatherDaySnapshot.setAddress(jsonNode.get("address").toString().replace("\"", "").replace(",", ""));
            weatherDaySnapshot.setDate(LocalDate.parse(jsonNode.get("days").get(i).get("datetime").toString().replace("\"", "")));
            weatherDaySnapshot.setAverageHumidity(Float.parseFloat(jsonNode.get("days").get(i).get("humidity").toString()));
            weatherDaySnapshot.setAverageTemperature(Float.parseFloat(jsonNode.get("days").get(i).get("temp").toString()));
            weatherDaySnapshot.setAverageWindSpeed(Float.parseFloat(jsonNode.get("days").get(i).get("windspeed").toString()));
            weatherDaySnapshotList.add(weatherDaySnapshot);
            i++;
        }
        return weatherDaySnapshotList;
    }

    private List<WeatherDuringDay> parseDataDuringDay(String data, List<WeatherDaySnapshot> weatherDaySnapshot, int pos) throws JsonProcessingException {

        JsonNode jsonNode = getJsonNode(data);

        List<WeatherDuringDay> weatherDuringDayList = new LinkedList<>();
        for (int i = 0; i < 24; i++) {
            WeatherDuringDay weatherDuringDay = new WeatherDuringDay();
            weatherDuringDay.setWeatherDaySnapshot(weatherDaySnapshot.get(pos));
            weatherDuringDay.setHour(i);
            weatherDuringDay.setTemp(Float.parseFloat(jsonNode.get("days").get(pos).get("hours").get(i).get("temp").toString()));
            weatherDuringDay.setHum(Float.parseFloat(jsonNode.get("days").get(pos).get("hours").get(i).get("humidity").toString()));
            weatherDuringDay.setWind(Float.parseFloat(jsonNode.get("days").get(pos).get("hours").get(i).get("windspeed").toString()));
            weatherDuringDayList.add(weatherDuringDay);
        }
        return weatherDuringDayList;
    }

    private static JsonNode getJsonNode(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(data);
    }

    private byte[] createGraphicsHours(List<WeatherDuringDay> weatherDuringDayList, String country, String city) throws IOException {

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
        return getBytes(country, city, hour, tempPerHour, windPerHour, humPerHour);
    }

    private byte[] createGraphicsDays(List<WeatherDaySnapshot> weatherDaySnapshotList, String country, String city) throws IOException {
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
        return getBytes(country, city, day, tempPerDay, windPerDay, humPerDay);
    }

    private byte[] getBytes(String country, String city, double[] dXStep, double[] temperature, double[] windSpeed, double[] humidity) throws IOException {
        XYChart chart = new XYChartBuilder().
                width(800).height(600).
                title(country + ", " + city).
                xAxisTitle("Hour").yAxisTitle("Amount").build();

        chart.addSeries("Temperature, F", dXStep, temperature);
        chart.addSeries("Windspeed*5, mi/h", dXStep, windSpeed);
        chart.addSeries("Humidity, %", dXStep, humidity);

        return BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.JPG);
    }
}
