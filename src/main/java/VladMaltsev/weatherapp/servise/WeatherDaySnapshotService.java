package VladMaltsev.weatherapp.servise;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.repositories.WeatherDaySnapshotRepo;
import VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapListDTOAndListClass;

@Service
@Slf4j
public class WeatherDaySnapshotService {
    private final WeatherDaySnapshotRepo weatherDaySnapshotRepo;

    public WeatherDaySnapshotService(WeatherDaySnapshotRepo weatherDaySnapshotRepo) {
        this.weatherDaySnapshotRepo = weatherDaySnapshotRepo;
    }

    public List<WeatherDaySnapshotDTO> insertNewWeatherSnapshot(List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO) {
        log.debug("WeatherDaySnapshotDTO after parsing JSON " + weatherDaySnapshotDTO.toString());
        List<WeatherDaySnapshot> weatherDaySnapshot = mapListDTOAndListClass(weatherDaySnapshotDTO, WeatherDaySnapshot.class);
        log.debug("WeatherDaySnapshot after convert DTO-Class " + weatherDaySnapshot);
        weatherDaySnapshotRepo.saveAll(weatherDaySnapshot);
        log.debug("WeatherDaySnapshot after save " + weatherDaySnapshot);
        return MappingDTOAndClass.mapListDTOAndListClass(weatherDaySnapshot, WeatherDaySnapshotDTO.class);
    }


}
