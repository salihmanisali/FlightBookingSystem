package com.iyzico.challenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentResponseDto {

  private static final long serialVersionUID = -8007346185017914954L;

  private FlightResponseDto flight;
  private List<String> seatNumbers;
  private String paymentStatus;

}
