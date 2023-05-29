package com.iyzico.challenge.controller;


import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.FlightResponseDto;
import com.iyzico.challenge.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "Flight", description = "API for flight operations.")
@RestController
@RequestMapping("v1/flight")
@RequiredArgsConstructor
public class FlightController {

  private final FlightService flightService;

  @Operation(summary = "Find All Flights", description = "Find all flights.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully found."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<FlightResponseDto> findAllFlights(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
    Pageable paging = PageRequest.of(page, pageSize);
    return flightService.findAllFlights(paging);
  }

  @Operation(summary = "Add Flight", description = "Add a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Successfully added.")
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FlightResponseDto addFlight(@Valid @RequestBody final FlightDto flightDto) {
    return flightService.addFlight(flightDto);
  }

  @Operation(summary = "Find Flight", description = "Find a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully found."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @GetMapping("/{flightId}")
  @ResponseStatus(HttpStatus.OK)
  public FlightResponseDto queryFlight(@PathVariable final String flightId) {
    return flightService.queryFlight(flightId);
  }

  @Operation(summary = "Update Flight", description = "Update a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "202", description = "Successfully updated."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @PatchMapping("/{flightId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public FlightResponseDto updateFlight(@PathVariable final String flightId,
                                        @Valid @RequestBody final FlightDto flightDto) {
    return flightService.updateFlight(flightId, flightDto);
  }

  @Operation(summary = "Remove Flight", description = "Remove a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Successfully removed."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @DeleteMapping("/{flightId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeFlight(@PathVariable final String flightId) {
    flightService.removeFlight(flightId);
  }
}
