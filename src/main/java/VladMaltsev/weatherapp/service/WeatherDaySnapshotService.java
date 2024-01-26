package VladMaltsev.weatherapp.service;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.repositories.WeatherDaySnapshotRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapDTOAndClass;
import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapListDTOAndListClass;

@Service
@Slf4j
public class WeatherDaySnapshotService {
    private final WeatherDaySnapshotRepo weatherDaySnapshotRepo;

    public WeatherDaySnapshotService(WeatherDaySnapshotRepo weatherDaySnapshotRepo) {
        this.weatherDaySnapshotRepo = weatherDaySnapshotRepo;
    }
    @Transactional
    public List<WeatherDaySnapshotDTO> insertNewWeatherSnapshot(List<WeatherDaySnapshotDTO> weatherDaySnapshotDTO) {
        log.debug("WeatherDaySnapshotDTO after parsing JSON " + weatherDaySnapshotDTO.toString());
        List<WeatherDaySnapshot> weatherDaySnapshot = mapListDTOAndListClass(weatherDaySnapshotDTO, WeatherDaySnapshot.class);
        log.debug("WeatherDaySnapshot after convert DTO-Class " + weatherDaySnapshot);
        weatherDaySnapshotRepo.saveAll(weatherDaySnapshot);
        log.debug("WeatherDaySnapshot after save " + weatherDaySnapshot);
        return mapListDTOAndListClass(weatherDaySnapshot, WeatherDaySnapshotDTO.class);
    }

    @Transactional(readOnly = true)
    public Optional<WeatherDaySnapshotDTO> findById(int id){
        return Optional.of(
                mapDTOAndClass(weatherDaySnapshotRepo.findById(id).orElseThrow()
                        , WeatherDaySnapshotDTO.class));
    }


}
