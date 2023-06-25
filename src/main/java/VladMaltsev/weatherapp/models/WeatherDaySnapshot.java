package VladMaltsev.weatherapp.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
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

    @OneToMany(mappedBy = "weatherDaySnapshot")
    private List<WeatherDuringDay> wduringDay;

    public WeatherDaySnapshot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(float averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public float getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(float averageHumidity) {
        this.averageHumidity = averageHumidity;
    }

    public float getAverageWindSpeed() {
        return averageWindSpeed;
    }

    public void setAverageWindSpeed(float averageWindSpeed) {
        this.averageWindSpeed = averageWindSpeed;
    }

    public List<WeatherDuringDay> getWduringDay() {
        return wduringDay;
    }

    public void setWduringDay(List<WeatherDuringDay> wduringDay) {
        this.wduringDay = wduringDay;
    }

    @Override
    public String toString() {
        return "WeatherDaySnapshot{" +
                "id=" + id +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", averageTemperature=" + averageTemperature +
                ", averageHumidity=" + averageHumidity +
                ", averageWindSpeed=" + averageWindSpeed +
                ", wduringDay=" + wduringDay +
                '}';
    }
}
