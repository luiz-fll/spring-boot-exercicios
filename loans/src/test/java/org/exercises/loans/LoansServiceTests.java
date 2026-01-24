package org.exercises.loans;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class LoansServiceTests {

    private LoansService loansService = new LoansService();

    /*
    Conceder o empréstimo pessoal se o salário do cliente for igual ou inferior a R$ 3000.
    Conceder o empréstimo pessoal se o salário do cliente estiver entre R$ 3000 e R$ 5000, se o cliente tiver menos de 30 anos e residir em São Paulo (SP).
    Conceder o empréstimo consignado se o salário do cliente for igual ou superior a R$ 5000.
    Conceder o empréstimo com garantia se o salário do cliente for igual ou inferior a R$ 3000.
    Conceder o empréstimo com garantia se o salário do cliente estiver entre R$ 3000 e R$ 5000, se o cliente tiver menos de 30 anos e residir em São Paulo (SP).
     */

    @Test
    void shouldConcedePersonalLoanForIncomeLowerThan3000() {
        LoansRequestDTO provided = new LoansRequestDTO(32, "123.456.789-00", "Fulano", 2500.00, "SP");
        LoansResponseDTO result = loansService.checkEligibleLoans(provided);

        assertThat(result.customer()).isEqualTo("Fulano");
        assertThat(result.loans().contains(Loan.PERSONAL));
    }

    @Test
    void shouldConcedePersonalLoanForIncomeBetween3000and5000atSP30yo() {
        LoansRequestDTO provided = new LoansRequestDTO(26, "123.456.789-00", "Fulano", 3500.00, "SP");
        LoansResponseDTO result = loansService.checkEligibleLoans(provided);

        assertThat(result.customer()).isEqualTo("Fulano");
        assertThat(result.loans().contains(Loan.PERSONAL));
    }

    @Test
    void shouldConcedeConsignmentLoanForIncomeGreaterThan5000() {
        LoansRequestDTO provided = new LoansRequestDTO(40, "123.456.789-00", "Fulano", 5000.00, "PR");
        LoansResponseDTO result = loansService.checkEligibleLoans(provided);

        assertThat(result.customer()).isEqualTo("Fulano");
        assertThat(result.loans().contains(Loan.CONSIGNMENT));
    }

    @Test
    void shouldConcedeGuaranteedLoanForIncomeLowerThan3000() {
        LoansRequestDTO provided = new LoansRequestDTO(32, "123.456.789-00", "Fulano", 2900.00, "PR");
        LoansResponseDTO result = loansService.checkEligibleLoans(provided);

        assertThat(result.customer()).isEqualTo("Fulano");
        assertThat(result.loans().contains(Loan.GUARANTEED));
    }

    @Test
    void shouldConcedeGuaranteedLoanForIncomeBetween3000and5000atSP30yo() {
        LoansRequestDTO provided = new LoansRequestDTO(28, "123.456.789-00", "Fulano", 3100.00, "SP");
        LoansResponseDTO result = loansService.checkEligibleLoans(provided);

        assertThat(result.customer()).isEqualTo("Fulano");
        assertThat(result.loans().contains(Loan.PERSONAL));
    }

}
