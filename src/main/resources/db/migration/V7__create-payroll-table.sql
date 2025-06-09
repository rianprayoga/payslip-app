START TRANSACTION;

CREATE TABLE payrolls(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    start_date   bigint  NOT NULL ,
    end_date  bigint  NOT NULL
);

COMMIT;