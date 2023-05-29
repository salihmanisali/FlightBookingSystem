package com.iyzico.challenge.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateSeatDto implements Serializable {

    private static final long serialVersionUID = 218195292886179492L;

    @NotNull
    @NotEmpty
    private List<String> seatNumbers;

    @NotNull
    @NotEmpty
    private String seatStatus;
}
