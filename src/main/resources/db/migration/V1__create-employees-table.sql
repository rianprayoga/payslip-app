START TRANSACTION;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE employees(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password TEXT NOT NULL,
    salary DECIMAL NOT NULL,
    CONSTRAINT salary_range CHECK (salary > 0)
);

CREATE UNIQUE INDEX unique_employees_username ON employees (username);

COMMIT;