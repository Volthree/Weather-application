package VladMaltsev.weatherapp.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "weather_summary")
public class WeatherDaySnapshot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "address")
    private String address;

    @Column(name = "avtemp")
    private float averageTemperature;
    @Column(name = "avhum")
    private float averageHumidity;
    @Column(name = "avwind")
    private float averageWindSpeed;

    @OneToMany(mappedBy = "summaryid")
    private Map<String, WeatherDuringDay> wduringDay;


}
