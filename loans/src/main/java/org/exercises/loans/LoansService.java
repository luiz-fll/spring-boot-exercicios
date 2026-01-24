package org.exercises.loans;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class LoansService {

    /*
    Conceder o empréstimo pessoal se o salário do cliente for igual ou inferior a R$ 3000.
    Conceder o empréstimo pessoal se o salário do cliente estiver entre R$ 3000 e R$ 5000, se o cliente tiver menos de 30 anos e residir em São Paulo (SP).
    Conceder o empréstimo consignado se o salário do cliente for igual ou superior a R$ 5000.
    Conceder o empréstimo com garantia se o salário do cliente for igual ou inferior a R$ 3000.
    Conceder o empréstimo com garantia se o salário do cliente estiver entre R$ 3000 e R$ 5000, se o cliente tiver menos de 30 anos e residir em São Paulo (SP).
     */

    public LoansResponseDTO checkEligibleLoans(LoansRequestDTO loansRequestDTO) {

        LinkedHashSet<Loan> loans = new LinkedHashSet<>();

        if (loansRequestDTO.income() <= 3000.00) {
            loans.add(Loan.PERSONAL);
            loans.add(Loan.GUARANTEED);
        }

        if (loansRequestDTO.income() > 3000.00
        && loansRequestDTO.income() < 5000.00
        && loansRequestDTO.age() < 30
        && loansRequestDTO.location().equals("SP")) {
            loans.add(Loan.PERSONAL);
            loans.add(Loan.GUARANTEED);
        }

        if (loansRequestDTO.income() >= 5000.00) {
            loans.add(Loan.CONSIGNMENT);
        }

        return new LoansResponseDTO(loansRequestDTO.name(), loans);
    }

}
