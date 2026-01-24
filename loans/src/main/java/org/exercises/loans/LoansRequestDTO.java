package org.exercises.loans;

public record LoansRequestDTO(
        Integer age,
        String cpf,
        String name,
        Double income,
        String location
) {}
