package com.iyzico.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iyzico.challenge.constants.SeatStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatListDto implements Serializable {

  private static final long serialVersionUID = 185544333369659675L;

  private List<SeatDto> seats = new ArrayList<>();

  private FlightResponseDto flight;
  @Data
  public static class SeatDto {
    @NotNull
    @NotEmpty
    private String seatNumber;

    @NotNull
    @NotEmpty
    private BigDecimal seatPrice;

    private SeatStatus seatStatus;
  }

}
