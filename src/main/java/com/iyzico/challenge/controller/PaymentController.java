package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.PaymentDto;
import com.iyzico.challenge.dto.PaymentResponseDto;
import com.iyzico.challenge.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@Tag(name = "Payment", description = "API for payment operations.")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/flight")
public class PaymentController {
  private final PaymentService iyzicoPaymentService;

  @Operation(summary = "Payment", description = "Make payment for flight ticket.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "202", description = "Successful payment."),
    @ApiResponse(responseCode = "400", description = "Payment failed because of bad request."),
    @ApiResponse(responseCode = "404", description = "Flight not found."),
    @ApiResponse(responseCode = "500", description = "Payment failed.")
  })
  @PostMapping("/{flightId}/payment")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public PaymentResponseDto createPayment(
    @PathVariable final String flightId,
    @Valid @RequestBody PaymentDto paymentDto) {

    return iyzicoPaymentService.createPayment(flightId, paymentDto);
  }
}
