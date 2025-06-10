package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.reimbusement.dto.PostReimburseRequest;
import com.example.payslip.data.repository.PayrollRepository;
import com.example.payslip.data.repository.ReimburseRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.utilities.DateHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeReimburseServiceTest {

    @Mock
    private ReimburseRepository reimburseRepository;

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private DateHelper dateHelper;

    @InjectMocks
    private EmployeeReimburseService service;

    @Test
    public void postReimburse_throwPayrollLocked() {

        User user = mock();
        PostReimburseRequest request = mock();

        when(payrollRepository.findCurrentPayrollBy(any())).thenReturn(Optional.of(UUID.randomUUID()));

        assertThatThrownBy(() -> service.postReimburse(user, request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Current payroll already locked.");
    }
}
