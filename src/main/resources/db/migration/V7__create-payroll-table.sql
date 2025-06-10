START TRANSACTION;

CREATE TABLE payrolls(
    id  UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    locked boolean default false,
    locked_date bigint,
    start_date   bigint  NOT NULL ,
    end_date  bigint  NOT NULL,
    created_at   bigint  NOT NULL ,
    updated_at  bigint  NOT NULL
);

COMMIT;