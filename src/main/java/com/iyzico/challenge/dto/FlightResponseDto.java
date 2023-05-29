package com.iyzico.challenge.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class FlightResponseDto implements Serializable {

    private static final long serialVersionUID = 9169465768409372073L;

    private String name;

    private String description;

    private String identifier;


}
