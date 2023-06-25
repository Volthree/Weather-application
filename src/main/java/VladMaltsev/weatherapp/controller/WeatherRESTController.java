package VladMaltsev.weatherapp.controller;

import VladMaltsev.weatherapp.Servise.WeatherDaySnapshotServise;
import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
public class WeatherRESTController {

    private final WeatherDaySnapshotServise weatherDaySnapshotServise;

    public WeatherRESTController(WeatherDaySnapshotServise weatherDaySnapshotServise) {
        this.weatherDaySnapshotServise = weatherDaySnapshotServise;
    }

    @GetMapping()
    public String getData(@RequestParam(name = "city") String city,
                          @RequestParam(name = "country") String country,
                          @RequestParam(name = "date") LocalDate date) throws JsonProcessingException {

        String data = getDataFromPage(city, country, date);
        WeatherDaySnapshot weatherDaySnapshot = parseData(data);
        weatherDaySnapshotServise.insertNewWeatherSnapshot(weatherDaySnapshot);
        return data;
    }

    private String getDataFromPage(String town, String country, LocalDate data) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://weather.visualcrossing.com/" +
                "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country + "/" + data +
                "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";

        return restTemplate.getForObject(url, String.class);
    }

    private WeatherDaySnapshot parseData(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        WeatherDaySnapshot weatherDaySnapshot = new WeatherDaySnapshot();
        weatherDaySnapshot.setAddress(jsonNode.get("address").toString().replace("\"", ""));
        System.out.println(jsonNode.get("days").get(0).get("datetime").toString());
        weatherDaySnapshot.setDate(LocalDate.parse(jsonNode.get("days").get(0).get("datetime").toString().replace("\"", "")));
        weatherDaySnapshot.setAverageHumidity(Float.parseFloat(jsonNode.get("days").get(0).get("humidity").toString()));
        weatherDaySnapshot.setAverageTemperature(Float.parseFloat(jsonNode.get("days").get(0).get("temp").toString()));
        weatherDaySnapshot.setAverageWindSpeed(Float.parseFloat(jsonNode.get("days").get(0).get("windspeed").toString()));
        return weatherDaySnapshot;
    }
}
