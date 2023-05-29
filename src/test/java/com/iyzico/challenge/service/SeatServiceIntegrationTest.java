package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.SeatListDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class SeatServiceIntegrationTest {


  @Autowired
  private SeatRepository seatRepository;

  @Autowired
  private FlightRepository flightRepository;

  @MockBean
  private FlightService flightService;

  @Autowired
  private ModelMapper modelMapper;

  @Test
  void addSeat() {
    // Arrange
    String flightId = "flightId";
    Flight flight = new Flight();
    flightRepository.save(flight);
    when(flightService.getFlight(flightId)).thenReturn(flight);

    SeatListDto dto = new SeatListDto();
    List<SeatListDto.SeatDto> seatDtos = new ArrayList<>();
    SeatListDto.SeatDto seatDto = new SeatListDto.SeatDto();
    seatDto.setSeatNumber("1A");
    seatDto.setSeatPrice(BigDecimal.valueOf(100));
    seatDtos.add(seatDto);
    dto.setSeats(seatDtos);

    SeatService seatService = new SeatService(seatRepository, flightService, modelMapper);

    // Act
    SeatListDto result = seatService.addSeat(flightId, dto);

    // Assert
    assertEquals(1, seatRepository.count());
    assertEquals(dto, result);
  }
}
