package VladMaltsev.weatherapp.dto;

import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDuringDayDTO {
    private int id;
    private WeatherDaySnapshot weatherDaySnapshot;
    private int hour;
    private float temp;
    private float hum;
    private float wind;
}
