package VladMaltsev.weatherapp.Servise;

import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import VladMaltsev.weatherapp.repositories.WeatherDaySnapshotRepo;
import org.springframework.stereotype.Service;

@Service
public class WeatherDaySnapshotService {
    private final WeatherDaySnapshotRepo weatherDaySnapshotRepo;

    public WeatherDaySnapshotService(WeatherDaySnapshotRepo weatherDaySnapshotRepo) {
        this.weatherDaySnapshotRepo = weatherDaySnapshotRepo;
    }

    public void insertNewWeatherSnapshot(WeatherDaySnapshot weatherDaySnapshot){
        weatherDaySnapshotRepo.save(weatherDaySnapshot);
    }
}
