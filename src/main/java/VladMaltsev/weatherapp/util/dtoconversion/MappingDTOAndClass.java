package VladMaltsev.weatherapp.util.dtoconversion;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MappingDTOAndClass {
    public static <D, C> C mapDTOAndClass(D dto, Class<C> clazz){
        ModelMapper modelMapper = new ModelMapper();
        C c = modelMapper.map(dto, clazz);
        return c;
    }

    public static <D, C> List<C> mapListDTOAndListClass(List<D> listDTO, Class<C> clazz) {
        List<C> weatherDaySnapshotList = new ArrayList<>();
        for (D dto: listDTO){
            weatherDaySnapshotList.add(mapDTOAndClass(dto, clazz));
        }
        return weatherDaySnapshotList;
    }
}
