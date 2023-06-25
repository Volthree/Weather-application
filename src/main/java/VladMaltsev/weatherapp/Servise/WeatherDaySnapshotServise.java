package VladMaltsev.weatherapp.Servise;

import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import VladMaltsev.weatherapp.repositories.WeatherDaySnapshotRepo;
import org.springframework.stereotype.Service;

@Service
public class WeatherDaySnapshotServise {
    private final WeatherDaySnapshotRepo weatherDaySnapshotRepo;

    public WeatherDaySnapshotServise(WeatherDaySnapshotRepo weatherDaySnapshotRepo) {
        this.weatherDaySnapshotRepo = weatherDaySnapshotRepo;
    }

    public void insertNewWeatherSnapshot(WeatherDaySnapshot weatherDaySnapshot){
        weatherDaySnapshotRepo.save(weatherDaySnapshot);
    }
}
