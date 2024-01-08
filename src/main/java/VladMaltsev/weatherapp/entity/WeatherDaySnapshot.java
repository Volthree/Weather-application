package VladMaltsev.weatherapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "weather_summary")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WeatherDaySnapshot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "weather_summary_id_seq", allocationSize = 1)
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

    @OneToMany(mappedBy = "weatherDaySnapshot")
    private List<WeatherDuringDay> wduringDay;

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
