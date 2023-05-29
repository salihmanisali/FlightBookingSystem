import com.iyzico.challenge.dto.SeatListDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.repository.SeatRepository;
import com.iyzico.challenge.service.FlightService;
import com.iyzico.challenge.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SeatServiceTest {

  @Mock
  private SeatRepository seatRepository;

  @Mock
  private FlightService flightService;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private SeatService seatService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void addSeat() {
    // Arrange
    String flightId = "flightId";
    Flight flight = new Flight();
    when(flightService.getFlight(flightId)).thenReturn(flight);

    SeatListDto dto = new SeatListDto();
    List<SeatListDto.SeatDto> seatDtos = new ArrayList<>();
    SeatListDto.SeatDto seatDto = new SeatListDto.SeatDto();
    seatDto.setSeatNumber("1A");
    seatDto.setSeatPrice(BigDecimal.valueOf(100));
    seatDtos.add(seatDto);
    dto.setSeats(seatDtos);

    // Act
    SeatListDto result = seatService.addSeat(flightId, dto);

    // Assert
    verify(seatRepository).saveAll(anyList());
    assertEquals(dto, result);
  }
}