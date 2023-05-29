package com.iyzico.challenge.service;


import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.FlightResponseDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FlightServiceUnitTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FlightService flightService;

    private FlightDto flightDto;
    private Flight flight;
    private FlightResponseDto flightResponseDto;

    @BeforeEach
    void setUp() {
        flightDto = new FlightDto();
        flightDto.setName("Flight 1");
        flightDto.setDescription("Description 1");

        flight = new Flight();
        flight.setName(flightDto.getName());
        flight.setDescription(flightDto.getDescription());
        flight.setIdentifier("identifier");

        flightResponseDto = FlightResponseDto.builder().build();
        flightResponseDto.setName(flight.getName());
        flightResponseDto.setDescription(flight.getDescription());
    }

    @Test
    void addFlightTest() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(modelMapper.map(any(Flight.class), eq(FlightResponseDto.class))).thenReturn(flightResponseDto);

        FlightResponseDto result = flightService.addFlight(flightDto);

        assertEquals(flightResponseDto.getName(), result.getName());
        assertEquals(flightResponseDto.getDescription(), result.getDescription());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void queryFlightTest() {
        String flightId = "1";
        when(flightRepository.findFlightByIdentifier(flightId)).thenReturn(Optional.of(flight));
        when(modelMapper.map(any(Flight.class), eq(FlightResponseDto.class))).thenReturn(flightResponseDto);

        FlightResponseDto result = flightService.queryFlight(flightId);

        assertEquals(flightResponseDto.getName(), result.getName());
        assertEquals(flightResponseDto.getDescription(), result.getDescription());
        verify(flightRepository).findFlightByIdentifier(flightId);
    }

    @Test
    void findAllFlightsTest() {
        Pageable paging = PageRequest.of(0, 10);

        List<Seat> seats = new ArrayList<>();


        List<Flight> flights = new ArrayList<>();
        flights.add(Flight.builder().identifier("Flight 1").build());
        flights.add(Flight.builder().identifier("Flight 2").build());

        List<FlightResponseDto> flightResponseDtos = new ArrayList<>();
        flightResponseDtos.add(FlightResponseDto.builder().identifier("Flight 1").build());
        flightResponseDtos.add(FlightResponseDto.builder().identifier("Flight 2").build());

        when(flightRepository.findAll(paging)).thenReturn(new PageImpl<>(flights));
        when(modelMapper.map(any(Flight.class), eq(FlightResponseDto.class)))
          .thenReturn(flightResponseDtos.get(0), flightResponseDtos.get(1));

        List<FlightResponseDto> result = flightService.findAllFlights(paging);

        assertEquals(flightResponseDtos.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(flightResponseDtos.get(i).getName(), result.get(i).getName());
            assertEquals(flightResponseDtos.get(i).getDescription(), result.get(i).getDescription());
        }
        verify(flightRepository).findAll(paging);
    }

    @Test
    void updateFlightTest() {
        // given
        String flightId = "1";
        FlightDto flightDto = new FlightDto();
        flightDto.setName("Updated Flight");
        flightDto.setDescription("Updated Description");

        Flight flight = new Flight();
        flight.setName("Original Flight");
        flight.setDescription("Original Description");

        Flight updatedFlight = new Flight();
        updatedFlight.setName(flightDto.getName());
        updatedFlight.setDescription(flightDto.getDescription());

        FlightResponseDto updatedFlightResponseDto = FlightResponseDto.builder().build();
        updatedFlightResponseDto.setName(updatedFlight.getName());
        updatedFlightResponseDto.setDescription(updatedFlight.getDescription());

        when(flightRepository.findFlightByIdentifier(flightId)).thenReturn(Optional.of(flight));
        when(modelMapper.map(any(Flight.class), eq(FlightResponseDto.class))).thenReturn(updatedFlightResponseDto);

        // when
        FlightResponseDto result = flightService.updateFlight(flightId, flightDto);

        // then
        assertEquals(updatedFlight.getName(), result.getName());
        assertEquals(updatedFlight.getDescription(), result.getDescription());
        verify(flightRepository).findFlightByIdentifier(flightId);
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void removeFlightTest() {
        String flightId = "1";
        String identifier = "identifier";
        when(flightRepository.findFlightByIdentifier(flightId)).thenReturn(Optional.of(flight));

        flightService.removeFlight(flightId);

        verify(flightRepository).findFlightByIdentifier(flightId);
        verify(flightRepository).deleteFlightByIdentifier(identifier);
    }

    @Test
    void getFlightTest() {
        String flightId = "1";
        when(flightRepository.findFlightByIdentifier(flightId)).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlight(flightId);

        assertEquals(flight.getName(), result.getName());
        assertEquals(flight.getDescription(), result.getDescription());
        verify(flightRepository).findFlightByIdentifier(flightId);
    }

    @Test
    void convertToDtoTest() {
        when(modelMapper.map(any(Flight.class), eq(FlightResponseDto.class))).thenReturn(flightResponseDto);

        FlightResponseDto result = flightService.convertToDto(flight);

        assertEquals(flightResponseDto.getName(), result.getName());
        assertEquals(flightResponseDto.getDescription(), result.getDescription());
        verify(modelMapper).map(any(Flight.class), eq(FlightResponseDto.class));
    }
}

