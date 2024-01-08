package VladMaltsev.weatherapp.util.getdata;

import VladMaltsev.weatherapp.exceptions.ImageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
public class GetDataByTownCityDate {

    public static String getDataFromSource(String town, String country, LocalDate date) throws ImageNotFoundException {
        log.debug("Start getDataFromSource method");
        RestTemplate restTemplate = new RestTemplate();
        String url;
        if (date == null) {
            log.debug("Date == null");
            url = "https://weather.visualcrossing.com/" +
                    "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country +
                    "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
        } else {
            log.debug("Date != null");
            url = "https://weather.visualcrossing.com/" +
                    "VisualCrossingWebServices/rest/services/timeline/" + town + "," + country + "/" + date +
                    "/?key=RDG2GVFL5SRQ9CU7S63N2WMNK";
        }

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

    public static String getData(String city, String country, LocalDate date){
        try {
            log.debug("Try getDataFromSource");
            return getDataFromSource(city, country, date);
        }
        catch (ImageNotFoundException e) {
            log.debug("Catch ImageNotFoundException: " + e.getMessage());
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Bad name Country or City or Date", e);
            throw e;
        }
    }
}
