package VladMaltsev.weatherapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;

@Service
@Slf4j
public class AddingModelService {

    public void addInModel(String city,
                           String country,
                           LocalDate date,
                           Model model){
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        model.addAttribute("date", date);
        log.debug("/singleday: city-" + city + " country-"+country+" date-"+date);
    }
}
