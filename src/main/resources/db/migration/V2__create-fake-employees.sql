START TRANSACTION;

INSERT INTO employees (username, "password", salary)
	SELECT
        concat('username_',i),
        UPPER(md5(concat('secret',i,'!'))),
        i * 100
    FROM generate_series(1, 100) as i;

COMMIT;