package com.iyzico.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENT", uniqueConstraints = {@UniqueConstraint(columnNames = {"SEAT_ID", "FLIGHT_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@OptimisticLocking
public class Payment extends BaseEntity {

  @Column(name = "PRICE")
  private BigDecimal price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SEAT_ID")
  private Seat seat;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FLIGHT_ID")
  private Flight flight;

  @Column(name = "BANK_RESPONSE")
  private String bankResponse;
}
