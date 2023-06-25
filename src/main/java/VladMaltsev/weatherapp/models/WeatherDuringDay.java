package VladMaltsev.weatherapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "weather_during_day")
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

    public WeatherDuringDay() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeatherDaySnapshot getWeatherDaySnapshot() {
        return weatherDaySnapshot;
    }

    public void setWeatherDaySnapshot(WeatherDaySnapshot weatherDaySnapshot) {
        this.weatherDaySnapshot = weatherDaySnapshot;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "WeatherDuringDay{" +
                "id=" + id +
                ", weatherDaySnapshot=" + weatherDaySnapshot +
                ", hour=" + hour +
                ", temp=" + temp +
                ", hum=" + hum +
                ", wind=" + wind +
                '}';
    }
}
