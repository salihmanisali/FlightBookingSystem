package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findFlightByIdentifier(String identifier);

    @Modifying
    void deleteFlightByIdentifier(String identifier);
}
