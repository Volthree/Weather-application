package VladMaltsev.weatherapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping()
public class WeatherController {

    @GetMapping()
    public String getMainPage(){
        return "pages/mainpage";
    }
    @GetMapping("/singleday")
    public String getSingleDay(@RequestParam(name = "city") String city,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "date") LocalDate date,
                               Model model){
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        model.addAttribute("date", date);
        return "pages/daypage";
    }
    @GetMapping("/lastdays")
    public String getLastDays(@RequestParam(name = "city") String city,
                               @RequestParam(name = "country") String country,
                               Model model){
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        return "pages/lastdays";
    }
}