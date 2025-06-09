START TRANSACTION;

CREATE TABLE payslip_admins(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password TEXT NOT NULL
);

CREATE UNIQUE INDEX unique_admin_username ON payslip_admins (username);

INSERT INTO payslip_admins (username, "password")
    VALUES ('admin1', crypt('secret',gen_salt('bf')));

COMMIT;