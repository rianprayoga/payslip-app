START TRANSACTION;

CREATE TABLE payslips(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    payroll_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    takehome_pay DECIMAL NOT NULL,
    detail JSONB,
    created_at   bigint  NOT NULL ,
    updated_at  bigint  NOT NULL,
    CONSTRAINT takehome_pay_range CHECK (takehome_pay > 0),
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees,
    CONSTRAINT fk_payroll_id FOREIGN KEY (payroll_id) REFERENCES payrolls
);

COMMIT;