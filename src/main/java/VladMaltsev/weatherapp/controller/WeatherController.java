package VladMaltsev.weatherapp.controller;


import VladMaltsev.weatherapp.service.AddingModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping()
@Slf4j
public class WeatherController {
    private final AddingModelService addingModelService;

    public WeatherController(AddingModelService addingModelService) {
        this.addingModelService = addingModelService;
    }

    @GetMapping()
    public String getMainPage() {
        return "pages/mainpage";
    }

    @GetMapping("/singleday")
    public String getSingleDay(@RequestParam(name = "city") String city,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "date") LocalDate date,
                               Model model) {
        addingModelService.addInModel(city, country, date, model);
        return "pages/daypage";
    }

    @GetMapping("/lastdays")
    public String getLastDays(@RequestParam(name = "city") String city,
                              @RequestParam(name = "country") String country,
                              Model model) {
        addingModelService.addInModel(city, country, null, model);
        return "pages/lastdays";
    }
}
