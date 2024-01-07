package VladMaltsev.weatherapp.repositories;

import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeatherDaySnapshotRepo extends JpaRepository<WeatherDaySnapshot, Integer> {

}
