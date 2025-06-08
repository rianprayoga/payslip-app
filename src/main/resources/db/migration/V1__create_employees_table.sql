CREATE TABLE employees(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password TEXT NOT NULL,
    salary BIGINT NOT NULL,
    CONSTRAINT salary_range CHECK (salary >= 0)
)