package com.iyzico.challenge.model;

import java.math.BigDecimal;

public class BankPaymentRequest {

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
