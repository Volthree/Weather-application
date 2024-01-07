package VladMaltsev.weatherapp.util.getdata;

import VladMaltsev.weatherapp.util.exceptions.ImageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Slf4j
public class GetDataByTownCityDate {

    public static String getDataFromSource(String town, String country, LocalDate date) throws ImageNotFoundException {
        log.error("Start getDataFromSource method");
        RestTemplate restTemplate = new RestTemplate();
        String url;
        if (date == null) {
            log.error("Date == null");
            url = "https://weather.visualcrossing.com/" +
                    "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country +
                    "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
        } else {
            log.error("Date != null");
            url = "https://weather.visualcrossing.com/" +
                    "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country + "/" + date +
                    "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
        }

        String jsonData;
        try {
            log.error("Try get jsonData");
            jsonData = restTemplate.getForObject(url, String.class);
            log.error("GsonData got success");
        } catch (HttpClientErrorException e) {
            log.error("HttpClientErrorException: " + e.getMessage());
            throw new ImageNotFoundException(e.getMessage());
        }
        return jsonData;
    }

    public static String getData(String city, String country, LocalDate date){
        try {
            log.error("Try getDataFromSource");
            return getDataFromSource(city, country, date);
        }
        catch (ImageNotFoundException e) {
            log.error("Catch ImageNotFoundException: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Bad name Country or City or Date", e);
        }
    }
}
