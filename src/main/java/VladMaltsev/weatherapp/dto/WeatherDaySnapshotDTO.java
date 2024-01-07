package VladMaltsev.weatherapp.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter

public class WeatherDaySnapshotDTO {

    private int id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String address;
    private float averageTemperature;
    private float averageHumidity;
    private float averageWindSpeed;

    @Override
    public String toString(){
        return Integer.toString(id);
    }
}
