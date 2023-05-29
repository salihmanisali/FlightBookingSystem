package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.FlightResponseDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest
class FlightServiceIntegrationTest {

  @Autowired
  private FlightService flightService;

  @Autowired
  private FlightRepository flightRepository;

  @Test
  void addFlightTest() {
    // given
    FlightDto flightDto = new FlightDto();
    flightDto.setName("Flight 1");
    flightDto.setDescription("Description 1");

    // when
    FlightResponseDto result = flightService.addFlight(flightDto);

    // then
    assertNotNull(result.getIdentifier());
    assertEquals(flightDto.getName(), result.getName());
    assertEquals(flightDto.getDescription(), result.getDescription());

    Optional<Flight> savedFlight = flightRepository.findFlightByIdentifier(result.getIdentifier());
    assertTrue(savedFlight.isPresent());
    assertEquals(flightDto.getName(), savedFlight.get().getName());
    assertEquals(flightDto.getDescription(), savedFlight.get().getDescription());
  }
}
