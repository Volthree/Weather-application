package VladMaltsev.weatherapp.controller;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.dto.WeatherDuringDayDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import VladMaltsev.weatherapp.service.WeatherDataService;
import VladMaltsev.weatherapp.service.WeatherDaySnapshotService;
import VladMaltsev.weatherapp.service.WeatherDuringDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping(value = "/image")
public class WeatherRESTController {

    private final WeatherDaySnapshotService weatherDaySnapshotService;
    private final WeatherDuringDayService weatherDuringDayService;
    private final WeatherDataService weatherDataService;

    public WeatherRESTController(WeatherDaySnapshotService weatherDaySnapshotService, WeatherDuringDayService weatherDuringDayService, WeatherDataService weatherDataService) {
        this.weatherDaySnapshotService = weatherDaySnapshotService;
        this.weatherDuringDayService = weatherDuringDayService;
        this.weatherDataService = weatherDataService;
    }

    @GetMapping(value = "/getsingleday")
    public byte[] getSingleDay(@RequestParam(name = "city") String city,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "date") LocalDate date) throws IOException {
        return weatherDataService.getWeatherBytea(city, country, date);
    }

    @GetMapping(value = "/getlastdays",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getLastTwoWeeks(@RequestParam(name = "city") String city,
                                  @RequestParam(name = "country") String country) throws IOException {
        return weatherDataService.getWeatherBytea(city, country, null);
    }

}
