package VladMaltsev.weatherapp.util.dtoconversion;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MappingDTOAndClass {
    private MappingDTOAndClass() {}

    public static <D, C> C mapDTOAndClass(D dto, Class<C> clazz) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, clazz);
    }

    public static <D, C> List<C> mapListDTOAndListClass(List<D> listDTO, Class<C> clazz) {
        List<C> weatherDaySnapshotList = new ArrayList<>();
        for (D dto : listDTO) {
            weatherDaySnapshotList.add(mapDTOAndClass(dto, clazz));
        }
        return weatherDaySnapshotList;
    }
}
