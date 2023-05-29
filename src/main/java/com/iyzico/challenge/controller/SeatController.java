package com.iyzico.challenge.controller;


import com.iyzico.challenge.dto.SeatListDto;
import com.iyzico.challenge.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@Tag(name = "Seat", description = "API for seat operations.")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/flight")
public class SeatController {

  private final SeatService seatService;

  @Operation(summary = "Add Seat", description = "Add seat for a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Successfully added."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @PostMapping("/{flightId}/seat")
  @ResponseStatus(HttpStatus.CREATED)
  public SeatListDto addSeat(@PathVariable final String flightId,
                              @Valid @RequestBody final SeatListDto seatDto) {
    return seatService.addSeat(flightId, seatDto);

  }

  @Operation(summary = "List Available Seats", description = "List available seats for a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully listed."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @GetMapping("/{flightId}/seat")
  public SeatListDto queryAvailableSeats(@PathVariable final String flightId) {
    return seatService.queryAvailableSeats(flightId);
  }

  @Operation(summary = "Update Seat", description = "Update seat for a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "202", description = "Successfully updated."),
    @ApiResponse(responseCode = "400", description = "Update operation failed."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @PatchMapping("/{flightId}/seat")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public SeatListDto updateSeat(@PathVariable final String flightId,
                                 @Valid @RequestBody final SeatListDto dto) {
    return seatService.updateSeat(flightId, dto);

  }

  @Operation(summary = "Remove Seat", description = "Remove seat for a flight.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Successfully removed."),
    @ApiResponse(responseCode = "400", description = "Delete operation failed."),
    @ApiResponse(responseCode = "404", description = "Flight not found.")
  })
  @DeleteMapping("/{flightId}/seat")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeSeat(@PathVariable final String flightId,
                         @RequestBody final Optional<List<String>> seatNumbers) {
    seatService.removeSeat(flightId, seatNumbers);
  }
}
