package VladMaltsev.weatherapp.util.getdata;

import VladMaltsev.weatherapp.exceptions.ImageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
@Component
public class GetDataByTownCityDate {
    private static String dataUrl1;
    private static String dataUrl2;
    private GetDataByTownCityDate() {}

    public static String getDataFromSource(String town, String country, LocalDate date) throws ImageNotFoundException {
        log.debug("Start getDataFromSource method");
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(dataUrl1 + " " + dataUrl2);
        String url = dataUrl1 + town + "," + country + (date != null ? "/" + date : "") + dataUrl2;
        String jsonData;
        try {
            log.debug("Try get jsonData");
            jsonData = restTemplate.getForObject(url, String.class);
            log.debug("JsonData got success");
        } catch (HttpClientErrorException e) {
            log.debug("HttpClientErrorException: " + e.getMessage());
            throw new ImageNotFoundException(e.getMessage());
        }
        return jsonData;
    }

    public static String getData(String city, String country, LocalDate date) {
        try {
            log.debug("Try getDataFromSource");
            return getDataFromSource(city, country, date);
        } catch (ImageNotFoundException e) {
            log.debug("Catch ImageNotFoundException: " + e.getMessage());
            throw e;
        }
    }

    @Value("${dataurl1}")
    public void setDataUrl1(String data) {
        dataUrl1 = data;
    }
    @Value("${dataurl2}")
    public void setDataUrl2(String data) {
        dataUrl2 = data;
    }
}
