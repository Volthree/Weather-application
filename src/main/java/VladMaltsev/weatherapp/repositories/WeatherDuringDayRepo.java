package VladMaltsev.weatherapp.repositories;

import VladMaltsev.weatherapp.entity.WeatherDuringDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface WeatherDuringDayRepo extends JpaRepository<WeatherDuringDay, Integer> {
}
