package VladMaltsev.weatherapp.repositories;

import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDaySnapshotRepo extends JpaRepository<WeatherDaySnapshot, Integer> {

}
