package org.exercises.loans;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    private final LoansService loansService;

    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }

    @PostMapping("/customer-loans")
    public LoansResponseDTO customerLoans(@RequestBody LoansRequestDTO loansRequestDTO) {
        return loansService.checkEligibleLoans(loansRequestDTO);
    }

}
