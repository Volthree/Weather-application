package VladMaltsev.weatherapp.util.getdata;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.dto.WeatherDuringDayDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapDTOAndClass;


@Slf4j
public class DataParser {
    private DataParser(){}

    public static List<WeatherDuringDayDTO> parseDataDuringDay(String data, List<WeatherDaySnapshotDTO> WeatherDaySnapshotDTO, int pos) throws JsonProcessingException {

        JsonNode jsonNode = getJsonNode(data);

        List<WeatherDuringDayDTO> WeatherDuringDayDTOList = new LinkedList<>();
        for (int i = 0; i < 24; i++) {
            WeatherDuringDayDTO WeatherDuringDayDTO = new WeatherDuringDayDTO();
            WeatherDuringDayDTO.setWeatherDaySnapshot(mapDTOAndClass(WeatherDaySnapshotDTO.get(pos), WeatherDaySnapshot.class));
            WeatherDuringDayDTO.setHour(i);
            WeatherDuringDayDTO.setTemp(Float.parseFloat(jsonNode.get("days").get(pos).get("hours").get(i).get("temp").toString()));
            WeatherDuringDayDTO.setHum(Float.parseFloat(jsonNode.get("days").get(pos).get("hours").get(i).get("humidity").toString()));
            WeatherDuringDayDTO.setWind(Float.parseFloat(jsonNode.get("days").get(pos).get("hours").get(i).get("windspeed").toString()));
            WeatherDuringDayDTOList.add(WeatherDuringDayDTO);
        }
        return WeatherDuringDayDTOList;
    }

    public static List<WeatherDaySnapshotDTO> parseDataForTwoWeeks(String data) throws JsonProcessingException {

        JsonNode jsonNode = getJsonNode(data);

        List<WeatherDaySnapshotDTO> WeatherDaySnapshotDTOList = new LinkedList<>();
        int i = 0;
        while (jsonNode.get("days").get(i) != null) {
            WeatherDaySnapshotDTO WeatherDaySnapshotDTO = new WeatherDaySnapshotDTO();
            WeatherDaySnapshotDTO.setAddress(jsonNode.get("address").toString().replace("\"", "").replace(",", ""));
            WeatherDaySnapshotDTO.setDate(LocalDate.parse(jsonNode.get("days").get(i).get("datetime").toString().replace("\"", "")));
            WeatherDaySnapshotDTO.setAverageHumidity(Float.parseFloat(jsonNode.get("days").get(i).get("humidity").toString()));
            WeatherDaySnapshotDTO.setAverageTemperature(Float.parseFloat(jsonNode.get("days").get(i).get("temp").toString()));
            WeatherDaySnapshotDTO.setAverageWindSpeed(Float.parseFloat(jsonNode.get("days").get(i).get("windspeed").toString()));
            WeatherDaySnapshotDTOList.add(WeatherDaySnapshotDTO);
            i++;
        }
        return WeatherDaySnapshotDTOList;
    }

    private static JsonNode getJsonNode(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(data);
    }
}
