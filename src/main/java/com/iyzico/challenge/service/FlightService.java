package com.iyzico.challenge.service;

import com.iyzico.challenge.constants.ErrorCode;
import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.FlightResponseDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.exception.FlightException;
import com.iyzico.challenge.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

  private final FlightRepository flightRepository;

  private final ModelMapper modelMapper;

  @Transactional
  public FlightResponseDto addFlight(final FlightDto flightDto) {
    Flight flight = new Flight();
    flight.setName(flightDto.getName());
    flight.setDescription(flightDto.getDescription());
    flight = flightRepository.save(flight);

    log.info("Flight saved successfully!");
    return convertToDto(flight);
  }

  @Transactional(readOnly = true)
  public FlightResponseDto queryFlight(final String flightId) {
    Flight flight = getFlight(flightId);
    return convertToDto(flight);
  }

  @Transactional(readOnly = true)
  public List<FlightResponseDto> findAllFlights(Pageable paging){
     return flightRepository.findAll(paging).map(this::convertToDto).get().collect(Collectors.toList());
  }
  @Transactional
  public FlightResponseDto updateFlight(final String flightId, final FlightDto flightDto) {
    Flight flight = getFlight(flightId);
    flight.setName(flightDto.getName());
    flight.setDescription(flightDto.getDescription());

    flightRepository.save(flight);

    log.info("Flight updated successfully!");

    return convertToDto(flight);
  }

  @Transactional
  public void removeFlight(final String flightId) {
    Flight flight = getFlight(flightId);
    flightRepository.deleteFlightByIdentifier(flight.getIdentifier());
    log.info("Flight removed successfully!");
  }

  public Flight getFlight(final String flightId) {
    Optional<Flight> flightOptional = flightRepository.findFlightByIdentifier(flightId);
    if (flightOptional.isEmpty()) {
      log.error("No flight found for flight identifier: {}", flightId);
      throw new FlightException(ErrorCode.FLIGHT_NOT_FOUND);
    }
    return flightOptional.get();
  }

  public FlightResponseDto convertToDto(Flight entity) {
    return modelMapper.map(entity, FlightResponseDto.class);
  }
}
