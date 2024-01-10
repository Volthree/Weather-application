package VladMaltsev.weatherapp.controller;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.dto.WeatherDuringDayDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import VladMaltsev.weatherapp.servise.WeatherDaySnapshotService;
import VladMaltsev.weatherapp.servise.WeatherDuringDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static VladMaltsev.weatherapp.util.ImageGeneration.GetImage.createGraphicsDays;
import static VladMaltsev.weatherapp.util.ImageGeneration.GetImage.createGraphicsHours;
import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapListDTOAndListClass;
import static VladMaltsev.weatherapp.util.getdata.DataParser.parseDataDuringDay;
import static VladMaltsev.weatherapp.util.getdata.DataParser.parseDataForTwoWeeks;
import static VladMaltsev.weatherapp.util.getdata.GetDataByTownCityDate.getData;

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
        log.debug("/getsingleday getData(): "+city+" "+country+" "+date);
        String data = getData(city, country, date);
        List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO = parseDataForTwoWeeks(data);
        weatherDaySnapshotDTO = weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshotDTO);
        log.debug("WeatherDaySnapshotDTO in Controller " + weatherDaySnapshotDTO.get(0));
        List<WeatherDuringDayDTO> w = parseDataDuringDay(data, weatherDaySnapshotDTO, 0);
        weatherDuringDayService.addListDuringDay(w);
        log.debug("After addListDuringDay (success)");
        return createGraphicsHours(mapListDTOAndListClass(w, WeatherDuringDay.class), country, city);
    }

    @GetMapping(value = "/getlastdays",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getLastTwoWeeks(@RequestParam(name = "city") String city,
                                  @RequestParam(name = "country") String country) throws IOException {
        log.debug("/getlastdays getData(): "+city+" "+country+" "+null);
        String data = getData(city, country, null);
        List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO = parseDataForTwoWeeks(data);
        weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshotDTO);
        log.debug("After insertNewWeatherSnapshot (success)");
        return createGraphicsDays(mapListDTOAndListClass(weatherDaySnapshotDTO, WeatherDaySnapshot.class), country, city);
    }

}
