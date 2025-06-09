START TRANSACTION;

CREATE TABLE employee_overtimes(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    employee_id UUID NOT NULL references employees(id),
    duration smallint NOT NULL ,
    CONSTRAINT duration_range CHECK (duration >= 1 AND duration <= 3),
    submission_date bigint NOT NULL,
    overtime_date bigint NOT NULL,
    created_at   bigint  NOT NULL ,
    updated_at  bigint  NOT NULL,
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees
);

COMMIT;