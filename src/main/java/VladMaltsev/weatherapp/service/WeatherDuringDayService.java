package VladMaltsev.weatherapp.service;

import VladMaltsev.weatherapp.dto.WeatherDuringDayDTO;
import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import VladMaltsev.weatherapp.repositories.WeatherDuringDayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapListDTOAndListClass;

@Service
public class WeatherDuringDayService {
    private final WeatherDuringDayRepo weatherDuringDayRepo;

    @Autowired
    public WeatherDuringDayService(WeatherDuringDayRepo weatherDuringDayRepo) {
        this.weatherDuringDayRepo = weatherDuringDayRepo;
    }

    @Transactional
    public void addListDuringDay(List<WeatherDuringDayDTO> weatherDuringDayListDTO){
        List<WeatherDuringDay> weatherDuringDayList = mapListDTOAndListClass(weatherDuringDayListDTO, WeatherDuringDay.class);
        weatherDuringDayRepo.saveAll(weatherDuringDayList);
    }
}
