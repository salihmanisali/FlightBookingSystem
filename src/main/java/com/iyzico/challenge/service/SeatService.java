package com.iyzico.challenge.service;


import com.iyzico.challenge.constants.ErrorCode;
import com.iyzico.challenge.constants.SeatStatus;
import com.iyzico.challenge.dto.SeatListDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.SeatException;
import com.iyzico.challenge.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeatService {

  private final SeatRepository seatRepository;
  private final FlightService flightService;
  private final ModelMapper modelMapper;

  @Transactional
  public SeatListDto addSeat(final String flightId, final SeatListDto dto) {
    Flight flight = flightService.getFlight(flightId);
    List<Seat> seats = new ArrayList<>();

    //Todo: MevcutMu kontrolü yok
    dto.getSeats().forEach(e -> {
      Seat seat = new Seat();
      seat.setSeatStatus(SeatStatus.AVAILABLE);
      seat.setSeatNumber(e.getSeatNumber());
      seat.setSeatPrice(e.getSeatPrice());
      seat.setSeatStatus(SeatStatus.AVAILABLE);
      seat.setFlight(flight);
      seats.add(seat);
    });
    dto.setFlight(flightService.convertToDto(flight));

    seatRepository.saveAll(seats);
    log.info("Seat saved successfully!");
    return dto;

  }


  @Transactional(readOnly = true)
  public SeatListDto queryAvailableSeats(final String flightId) {
    Flight flight = flightService.getFlight(flightId);

    List<Seat> availableSeats = getAvailableSeats(flight, SeatStatus.AVAILABLE);

    return convertToDto(flight, availableSeats);
  }

  @Transactional
  public SeatListDto updateSeat(final String flightId, final SeatListDto dto) {
    Flight flight = flightService.getFlight(flightId);
    List<Seat> seats = new ArrayList<>();

    dto.getSeats().forEach(e -> {
      Seat seat = getSeat(flight, e.getSeatNumber());

      if (SeatStatus.SOLD.equals(seat.getSeatStatus())) {
        log.error("Seat can not be updated since it's Sold for seat number: {}", seat.getSeatNumber());
        throw new SeatException(ErrorCode.SEAT_OPERATION_FAILED);
      }

      seat.setSeatStatus(e.getSeatStatus());
      seat.setSeatNumber(e.getSeatNumber());
      seat.setSeatPrice(e.getSeatPrice());
      seat.setFlight(flight);
      seats.add(seat);
    });
    dto.setFlight(flightService.convertToDto(flight));

    seatRepository.saveAll(seats);
    log.info("Seat updated successfully!");
    return dto;


  }

  @Transactional
  public void removeSeat(final String flightId, final Optional<List<String>> seatNumbersOptional) {
    if(seatNumbersOptional.isEmpty()) throw new SeatException(ErrorCode.SEAT_NOT_FOUND);

    Flight flight = flightService.getFlight(flightId);
    List<String> seatNumbers = seatNumbersOptional.get();

    for (String seatNumber : seatNumbers) {
      Seat seat = getSeat(flight, seatNumber);
      if (SeatStatus.SOLD.equals(seat.getSeatStatus())) {
        log.error("Seat can not be removed since it's Sold for seat number: {}", seat.getSeatNumber());
        throw new SeatException(ErrorCode.SEAT_OPERATION_FAILED);
      }
      seatRepository.deleteSeatByFlightAndSeatNumber(flight, seat.getSeatNumber());
      log.info("Seat removed successfully!");
    }
  }

  public Seat getSeat(final Flight flight, final String seatNumber) {
    Optional<Seat> seatOptional = seatRepository.findSeatByFlightAndSeatNumber(flight, seatNumber);
    if (seatOptional.isEmpty()) {
      log.error("No seat found for seat number: {}", seatNumber);
      throw new SeatException(ErrorCode.SEAT_NOT_FOUND);
    }
    return seatOptional.get();
  }

  public void updateSeatStatus(final List<Seat> seats, final SeatStatus seatStatus) {
    for (Seat seat : seats) {
      if (SeatStatus.SOLD.equals(seat.getSeatStatus())) {
        log.error("Seat can not be updated since it's Sold for seat number: {}", seat.getSeatNumber());
        throw new SeatException(ErrorCode.SEAT_OPERATION_FAILED);
      }
      seat.setSeatStatus(seatStatus);
    }
    log.info("Seat updated successfully!");
    seatRepository.saveAll(seats);
  }

  public void updateSeatStatus(final Flight flight, final List<String> seatNumbers, final SeatStatus seatStatus) {
    List<Seat> seats = seatRepository.findSeatByFlightAndSeatNumberIn(flight, seatNumbers);
    if (seats.size() < seatNumbers.size())
      throw new SeatException(ErrorCode.SEAT_OPERATION_UPDATE_FAILED);

    updateSeatStatus(seats, seatStatus);
  }

  private List<Seat> getAvailableSeats(final Flight flight, final SeatStatus seatStatus) {
    List<Seat> availableSeatsOptional = seatRepository.findSeatsByFlightAndSeatStatus(flight, SeatStatus.AVAILABLE);

    if (availableSeatsOptional.isEmpty()) {
      log.error("No available seat found for flight: {}", flight.getIdentifier());
      throw new SeatException(ErrorCode.AVAILABLE_SEAT_NOT_FOUND);
    }

    return availableSeatsOptional;
  }

  private SeatListDto convertToDto(Flight flight, List<Seat> availableSeats) {
    SeatListDto dto = new SeatListDto();
    dto.setFlight(flightService.convertToDto(flight));
    availableSeats.forEach(e -> dto.getSeats().add(convertToDto(e)));
    return dto;
  }

  private SeatListDto.SeatDto convertToDto(Seat entity) {
    return modelMapper.map(entity, SeatListDto.SeatDto.class);
  }
}
