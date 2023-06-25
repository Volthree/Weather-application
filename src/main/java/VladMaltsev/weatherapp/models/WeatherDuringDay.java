package VladMaltsev.weatherapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "weather_during_day")
public class WeatherDuringDay {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "summaryid")
    @ManyToOne
    private WeatherDaySnapshot weatherDaySnapshot;
    @Column(name = "hour")
    private int hour;
    @Column(name = "temp")
    private float temp;
    @Column(name = "hum")
    private float hum;
    @Column(name = "wind")
    private float wind;

}
