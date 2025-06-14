START TRANSACTION;

CREATE TABLE employee_attendances(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    employee_id UUID NOT NULL references employees(id),
    attendance_date bigint NOT NULL ,
    check_in bigint ,
    check_out bigint ,
    created_at  bigint  NOT NULL ,
    updated_at  bigint  NOT NULL,
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees
);

COMMIT;