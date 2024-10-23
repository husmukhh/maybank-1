package com.fileread.maybank;

import java.math.BigDecimal;

public class Rate {

    private String rate;
    private BigDecimal value;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
