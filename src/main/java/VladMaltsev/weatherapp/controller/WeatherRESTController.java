package VladMaltsev.weatherapp.controller;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.dto.WeatherDuringDayDTO;
import VladMaltsev.weatherapp.servise.WeatherDaySnapshotService;
import VladMaltsev.weatherapp.servise.WeatherDuringDayService;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapListDTOAndListClass;
import static VladMaltsev.weatherapp.util.getdata.GetDataByTownCityDate.*;
import static VladMaltsev.weatherapp.util.getdata.DataParser.*;
import static VladMaltsev.weatherapp.util.ImageGeneration.GetImage.*;

@RestController
@Slf4j
@RequestMapping(value = "/image")
public class WeatherRESTController {

    private final WeatherDaySnapshotService weatherDaySnapshotService;
    private final WeatherDuringDayService weatherDuringDayService;

    public WeatherRESTController(WeatherDaySnapshotService weatherDaySnapshotService, WeatherDuringDayService weatherDuringDayService) {
        this.weatherDaySnapshotService = weatherDaySnapshotService;
        this.weatherDuringDayService = weatherDuringDayService;
    }

    @GetMapping(value = "/getsingleday")
    public byte[] getSingleDay(@RequestParam(name = "city") String city,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "date") LocalDate date) throws IOException {
        log.error("Enter GetSingleDay method");
        String data = getData(city, country, date);
        log.error("After getData method");
        List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO = parseDataForTwoWeeks(data);
        log.error("After parseDataForTwoWeeks");
        weatherDaySnapshotDTO = weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshotDTO);
        log.error("After insertNewWeatherSnapshot in singleDay");
        log.error("WeatherDaySnapshotDTO in Controller " + weatherDaySnapshotDTO.get(0));
        List<WeatherDuringDayDTO> w = parseDataDuringDay(data, weatherDaySnapshotDTO, 0);
        log.error("After parseDataDuringDay");
        weatherDuringDayService.addListDuringDay(w);
        log.error("After addListDuringDay");
        return createGraphicsHours(mapListDTOAndListClass(w, WeatherDuringDay.class), country, city);
    }

    @GetMapping(value = "/getlastdays",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getLastTwoWeeks(@RequestParam(name = "city") String city,
                                  @RequestParam(name = "country") String country) throws IOException {

        String data = getData(city, country, null);
        List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO = parseDataForTwoWeeks(data);
        weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshotDTO);

        return createGraphicsDays(mapListDTOAndListClass(weatherDaySnapshotDTO, WeatherDaySnapshot.class), country, city);
    }

}
