package com.iyzico.challenge.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "FLIGHT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight extends BaseEntity {

  @Column(unique = true)
  private String identifier;

  @Column(name = "FLIGHT_NAME", unique = true)
  private String name;

  @Column(name = "FLIGHT_DESCRIPTION")
  private String description;

  @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Seat> seats;

  @PrePersist
  void prePersist() {
    this.identifier = UUID.randomUUID().toString();
  }

}
