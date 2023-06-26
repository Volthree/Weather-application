package VladMaltsev.weatherapp.repositories;

import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import VladMaltsev.weatherapp.models.WeatherDuringDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDuringDayRepo extends JpaRepository<WeatherDuringDay, Integer> {
}
