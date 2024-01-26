package VladMaltsev.weatherapp;

import VladMaltsev.weatherapp.dto.WeatherDaySnapshotDTO;
import VladMaltsev.weatherapp.entity.WeatherDaySnapshot;
import VladMaltsev.weatherapp.repositories.WeatherDaySnapshotRepo;
import VladMaltsev.weatherapp.repositories.WeatherDuringDayRepo;
import VladMaltsev.weatherapp.service.WeatherDaySnapshotService;
import VladMaltsev.weatherapp.service.WeatherDuringDayService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static VladMaltsev.weatherapp.util.dtoconversion.MappingDTOAndClass.mapDTOAndClass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class WeatherappApplicationTests {
	private final int ID = 99;
	@Mock
	private WeatherDaySnapshotRepo weatherDaySnapshotRepo;
	@InjectMocks
	private WeatherDaySnapshotService weatherDaySnapshotService;

	@Test
	void findDaySnapshotById() {
		Mockito.doReturn(Optional.of(WeatherDaySnapshotDTO.builder().id(ID).build()))
				.when(weatherDaySnapshotRepo).findById(ID);

		var res = weatherDaySnapshotService.findById(ID);
		assertTrue(res.isPresent());
		var exres = WeatherDaySnapshotDTO.builder().id(ID).build();
		res.ifPresent(x -> assertEquals(x, exres));
	}

	@Test
	void DTOTest(){
		WeatherDaySnapshotDTO dto = WeatherDaySnapshotDTO.builder().id(ID).build();
		WeatherDaySnapshot obj = mapDTOAndClass(dto, WeatherDaySnapshot.class);
		System.out.println(obj);
		WeatherDaySnapshot obj1 = WeatherDaySnapshot.builder().id(ID).build();
		WeatherDaySnapshotDTO dto1 = mapDTOAndClass(obj1, WeatherDaySnapshotDTO.class);
		System.out.println(dto1);
	}

}
