package org.exercises.loans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum Loan {

    PERSONAL(4),
    GUARANTEED(3),
    CONSIGNMENT(2);

    private final String type;
    @JsonProperty("interest_rate")
    private final Integer interestRate;

    Loan(Integer interestRate) {
        this.type = name();
        this.interestRate = interestRate;
    }

    public String getType() {
        return type;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

}
