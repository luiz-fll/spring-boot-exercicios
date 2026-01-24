package org.exercises.loans;

import java.util.LinkedHashSet;

public record LoansResponseDTO(
        String customer,
        LinkedHashSet<Loan> loans
) {}
