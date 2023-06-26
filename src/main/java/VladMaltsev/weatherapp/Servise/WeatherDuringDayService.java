package VladMaltsev.weatherapp.Servise;

import VladMaltsev.weatherapp.models.WeatherDuringDay;
import VladMaltsev.weatherapp.repositories.WeatherDuringDayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDuringDayService {
    private final WeatherDuringDayRepo weatherDuringDayRepo;

    @Autowired
    public WeatherDuringDayService(WeatherDuringDayRepo weatherDuringDayRepo) {
        this.weatherDuringDayRepo = weatherDuringDayRepo;
    }

    public void addListDuringDay(List<WeatherDuringDay> weatherDuringDayList){
        weatherDuringDayRepo.saveAll(weatherDuringDayList);
    }
}
