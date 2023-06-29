package VladMaltsev.weatherapp.Servise;

import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import VladMaltsev.weatherapp.repositories.WeatherDaySnapshotRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDaySnapshotService {
    private final WeatherDaySnapshotRepo weatherDaySnapshotRepo;

    public WeatherDaySnapshotService(WeatherDaySnapshotRepo weatherDaySnapshotRepo) {
        this.weatherDaySnapshotRepo = weatherDaySnapshotRepo;
    }

    public void insertNewWeatherSnapshot(List<WeatherDaySnapshot> weatherDaySnapshot){
        for(WeatherDaySnapshot w : weatherDaySnapshot) {
            weatherDaySnapshotRepo.save(w);
        }
    }
}
