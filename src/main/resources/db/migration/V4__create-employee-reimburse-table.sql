START TRANSACTION;

CREATE TABLE employee_reimbursements(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    employee_id UUID NOT NULL references employees(id),
    amount decimal NOT NULL ,
    CONSTRAINT amount_range CHECK (amount > 0),
    description VARCHAR(100) NOT NULL,
    submission_date   bigint  NOT NULL ,
    created_at   bigint  NOT NULL ,
    updated_at  bigint  NOT NULL,
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees
);

COMMIT;