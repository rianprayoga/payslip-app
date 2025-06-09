START TRANSACTION;

CREATE TABLE employee_attendances(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    employee_id UUID NOT NULL references employees(id),
    attendance_date bigint NOT NULL ,
    check_in bigint ,
    check_out bigint ,
    created_on   bigint  NOT NULL ,
    modified_on  bigint  NOT NULL
);

COMMIT;