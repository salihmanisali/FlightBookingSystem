package com.iyzico.challenge.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  INVALID_INPUT("IYZ0001", HttpStatus.NOT_FOUND),
  FLIGHT_NOT_FOUND("IYZ0002", HttpStatus.NOT_FOUND),
  AVAILABLE_SEAT_NOT_FOUND("IYZ0003", HttpStatus.NOT_FOUND),
  SEAT_NOT_FOUND("IYZ0004", HttpStatus.NOT_FOUND),
  INVALID_SEAT_STATUS("IYZ0005", HttpStatus.NOT_FOUND),
  SEAT_ALREADY_SOLD("IYZ0006",  HttpStatus.NOT_FOUND),
  PAYMENT_FAILED("IYZ0007", HttpStatus.NOT_FOUND),
  SEAT_OPERATION_UPDATE_FAILED("IYZ0008", HttpStatus.NOT_FOUND),
  SEAT_OPERATION_FAILED("IYZ0009", HttpStatus.NOT_FOUND);

  private final String code;
  private final HttpStatus httpStatus;


}
