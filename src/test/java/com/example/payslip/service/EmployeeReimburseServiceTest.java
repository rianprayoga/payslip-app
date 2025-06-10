package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.reimbusement.dto.PostReimburseRequest;
import com.example.payslip.controller.reimbusement.dto.PostReimburseResponse;
import com.example.payslip.data.entity.EmployeeReimbursementEntity;
import com.example.payslip.data.repository.PayrollRepository;
import com.example.payslip.data.repository.ReimburseRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.utilities.DateHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    public void postReimburse_success() {
        ArgumentCaptor<EmployeeReimbursementEntity> captor = ArgumentCaptor.forClass(EmployeeReimbursementEntity.class);

        UUID id = UUID.randomUUID();
        User user = mock();
        when(user.getId()).thenReturn(id);

        PostReimburseRequest request = mock();
        when(request.getAmount()).thenReturn(BigDecimal.TEN);
        when(request.getDescription()).thenReturn("Desc");

        when(payrollRepository.findCurrentPayrollBy(any())).thenReturn(Optional.empty());

        PostReimburseResponse response = service.postReimburse(user, request);

        verify(reimburseRepository, times(1)).save(captor.capture());

        EmployeeReimbursementEntity entity = captor.getValue();
        assertThat(entity)
                .returns(id, EmployeeReimbursementEntity::getEmployeeId)
                .returns(BigDecimal.TEN, EmployeeReimbursementEntity::getAmount);

        assertThat(response).isNotNull().returns("Reimbursement added.", PostReimburseResponse::getMessage);
    }
}
