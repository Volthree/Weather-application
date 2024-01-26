package VladMaltsev.weatherapp.service;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.dto.WeatherDuringDayDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static VladMaltsev.weatherapp.util.ImageGeneration.GetImage.createGraphicsDays;
import static VladMaltsev.weatherapp.util.ImageGeneration.GetImage.createGraphicsHours;
import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapListDTOAndListClass;
import static VladMaltsev.weatherapp.util.getdata.DataParser.parseDataDuringDay;
import static VladMaltsev.weatherapp.util.getdata.DataParser.parseDataForTwoWeeks;
import static VladMaltsev.weatherapp.util.getdata.GetDataByTownCityDate.getData;

@Service
public class WeatherDataService {

    private final WeatherDaySnapshotService weatherDaySnapshotService;
    private final WeatherDuringDayService weatherDuringDayService;

    public WeatherDataService(WeatherDaySnapshotService weatherDaySnapshotService, WeatherDuringDayService weatherDuringDayService) {
        this.weatherDaySnapshotService = weatherDaySnapshotService;
        this.weatherDuringDayService = weatherDuringDayService;
    }

    public byte[] getWeatherBytea(String city,
                                  String country,
                                  LocalDate date) throws IOException {
        String data = getData(city, country, date);
        List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO = parseDataForTwoWeeks(data);
        weatherDaySnapshotDTO = weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshotDTO);
        List<WeatherDuringDayDTO> w = parseDataDuringDay(data, weatherDaySnapshotDTO, 0);
        weatherDuringDayService.addListDuringDay(w);
        if (date != null)
            return createGraphicsHours(mapListDTOAndListClass(w, WeatherDuringDay.class), country, city);
        else{
            return createGraphicsDays(mapListDTOAndListClass(weatherDaySnapshotDTO, WeatherDaySnapshot.class), country, city);
        }
    }
}
