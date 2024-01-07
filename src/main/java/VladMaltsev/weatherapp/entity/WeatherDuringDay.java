package VladMaltsev.weatherapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather_during_day")
@Data
@NoArgsConstructor
public class WeatherDuringDay {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "summaryid", referencedColumnName = "id")
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
