package VladMaltsev.weatherapp.controller;

import VladMaltsev.weatherapp.Servise.WeatherDaySnapshotService;
import VladMaltsev.weatherapp.Servise.WeatherDuringDayService;
import VladMaltsev.weatherapp.models.WeatherDaySnapshot;
import VladMaltsev.weatherapp.models.WeatherDuringDay;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.Response;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.series.Series;
import org.knowm.xchart.style.Styler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.service.invoker.UrlArgumentResolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = "/weather")
public class WeatherRESTController {

    private final WeatherDaySnapshotService weatherDaySnapshotService;
    private final WeatherDuringDayService weatherDuringDayService;

    public WeatherRESTController(WeatherDaySnapshotService weatherDaySnapshotService, WeatherDuringDayService weatherDuringDayService) {
        this.weatherDaySnapshotService = weatherDaySnapshotService;
        this.weatherDuringDayService = weatherDuringDayService;
    }

    @GetMapping()
    public String getData(@RequestParam(name = "city") String city,
                          @RequestParam(name = "country") String country,
                          @RequestParam(name = "date") LocalDate date,
                          Model model) throws IOException {

        String data = getDataFromPage(city, country, date);
        WeatherDaySnapshot weatherDaySnapshot = parseData(data);
        weatherDaySnapshotService.insertNewWeatherSnapshot(weatherDaySnapshot);
        List<WeatherDuringDay> w = parseDataDuringDay(data, weatherDaySnapshot);

        Image i = createGraphics(w, country, city);

        weatherDuringDayService.addListDuringDay(w);
        model.addAttribute("day", weatherDaySnapshot);
        model.addAttribute("dayperhour", w);
        model.addAttribute("city", city);
        model.addAttribute("image", i);

        return "pages/daypage";
    }

    private String getDataFromPage(String town, String country, LocalDate data) throws IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://weather.visualcrossing.com/" +
//                "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country + "/" + data +
//                "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
//        String resJSON = restTemplate.getForObject(url, String.class);
//
//        byte[] bytes = resJSON.getBytes();
//        FileOutputStream fo = new FileOutputStream("E:\\All Java\\weatherapp\\src\\main\\resources\\static\\JSONData.json");
//        fo.write(bytes);


        byte[] b = Files.readAllBytes(Paths.get("E:\\All Java\\weatherapp\\src\\main\\resources\\static\\JSONData.json"));
        String resJSON = new String(b);
        //////////////

        return resJSON;
    }

    private WeatherDaySnapshot parseData(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        WeatherDaySnapshot weatherDaySnapshot = new WeatherDaySnapshot();
        weatherDaySnapshot.setAddress(jsonNode.get("address").toString().replace("\"", "").replace(",", ""));
        weatherDaySnapshot.setDate(LocalDate.parse(jsonNode.get("days").get(0).get("datetime").toString().replace("\"", "")));

        weatherDaySnapshot.setAverageHumidity(Float.parseFloat(jsonNode.get("days").get(0).get("humidity").toString()));
        weatherDaySnapshot.setAverageTemperature(Float.parseFloat(jsonNode.get("days").get(0).get("temp").toString()));
        weatherDaySnapshot.setAverageWindSpeed(Float.parseFloat(jsonNode.get("days").get(0).get("windspeed").toString()));
        return weatherDaySnapshot;
    }

    private List<WeatherDuringDay> parseDataDuringDay(String data, WeatherDaySnapshot weatherDaySnapshot) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        List<WeatherDuringDay> weatherDuringDayList = new LinkedList<>();
        for (int i = 0; i < 24; i++) {
            WeatherDuringDay weatherDuringDay = new WeatherDuringDay();
            weatherDuringDay.setWeatherDaySnapshot(weatherDaySnapshot);
            weatherDuringDay.setHour(i);
            weatherDuringDay.setTemp(Float.parseFloat(jsonNode.get("days").get(0).get("hours").get(i).get("temp").toString()));
            weatherDuringDay.setHum(Float.parseFloat(jsonNode.get("days").get(0).get("hours").get(i).get("humidity").toString()));
            weatherDuringDay.setWind(Float.parseFloat(jsonNode.get("days").get(0).get("hours").get(i).get("windspeed").toString()));
            weatherDuringDayList.add(weatherDuringDay);
        }
        return weatherDuringDayList;
    }

    private Image createGraphics(List<WeatherDuringDay> weatherDuringDayList, String country, String city) throws IOException {

        double[] hour = new double[24];
        double[] tempPerHour = new double[24];
        double[] windPerHour = new double[24];
        double[] humPerHour = new double[24];
        int i = 0;
        for(WeatherDuringDay w : weatherDuringDayList){
            hour[i] = i;
            tempPerHour[i] = w.getTemp();
            windPerHour[i] = w.getWind();
            humPerHour[i] = w.getHum();
            i++;
        }
        XYChart chart = new XYChartBuilder().
                width(800).height(600).
                title(country + ", " + city).
                xAxisTitle("Hour").yAxisTitle("Amount").build();

        chart.addSeries("Temp", hour, tempPerHour);
        chart.addSeries("Wind", hour, windPerHour);
        chart.addSeries("Hum", hour, humPerHour);

//        FileOutputStream fo = new FileOutputStream("src/main/resources/static/img/"+ city+country+".jpg");
//        BitmapEncoder.saveBitmap(chart, fo, BitmapEncoder.BitmapFormat.JPG);
        byte[] s = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.JPG);
        ByteArrayInputStream bais = new ByteArrayInputStream(s);
        BufferedImage image = ImageIO.read(bais);
//        File f = new File("src/main/resources/static/img/"+ city+country+".jpg");
        File f = new File("charts/"+ city+country+".jpg");
        ImageIO.write(image, "jpg", f);


        Image im = new BufferedImage(600, 800, BufferedImage.TYPE_INT_RGB);
        Image image1 = ImageIO.read(bais);
        return image1;
    }
}
