package com.iyzico.challenge.repository;

import com.iyzico.challenge.constants.SeatStatus;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;


public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Seat> findSeatByFlightAndSeatNumber(Flight flight, String seatNumber);

    List<Seat> findSeatByFlightAndSeatNumberIn(Flight flight, List<String> seatNumbers);
    List<Seat> findSeatsByFlightAndSeatStatus(Flight flight, SeatStatus seatStatus);

    @Modifying
    void deleteSeatByFlightAndSeatNumber(Flight flight, String seatNumber);

}
