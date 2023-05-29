package com.iyzico.challenge.entity;

import com.iyzico.challenge.constants.SeatStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SEAT", uniqueConstraints = {@UniqueConstraint(columnNames = {"SEAT_NUMBER", "FLIGHT_ID"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat extends BaseEntity {

  @Column(name = "SEAT_NUMBER")
  private String seatNumber;

  @Column
  @Enumerated(EnumType.STRING)
  @ToString.Include
  private SeatStatus seatStatus;

  @Column(name = "SEAT_PRICE")
  private BigDecimal seatPrice;

  @ManyToOne
  @JoinColumn(name = "FLIGHT_ID")
  private Flight flight;
}
