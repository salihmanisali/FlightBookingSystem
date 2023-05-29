package com.iyzico.challenge.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FlightDto implements Serializable {

    private static final long serialVersionUID = 9169465768409372073L;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

}
