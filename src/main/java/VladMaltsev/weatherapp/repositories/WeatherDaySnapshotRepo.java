package VladMaltsev.weatherapp.repositories;

import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface WeatherDaySnapshotRepo extends JpaRepository<WeatherDaySnapshot, Integer> {

}
