package com.iyzico.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDto implements Serializable {

    private static final long serialVersionUID = -9131952628646484154L;

    @NotNull
    @NotEmpty
    private List<String> seatNumbers;

    @NotNull
    @NotEmpty
    private String cardHolderName;

    @NotNull
    @NotEmpty
    private String cardNumber;

    @NotNull
    @NotEmpty
    private String cardExpireMonth;

    @NotNull
    @NotEmpty
    private String cardExpireYear;

    @NotNull
    @NotEmpty
    private String cardCvc;
}
