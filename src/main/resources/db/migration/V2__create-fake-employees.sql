START TRANSACTION;

INSERT INTO employees (username, "password", salary)
	SELECT
        concat('username_',i),
        crypt(
            concat('secret',i),
            gen_salt('bf')
            ),
        i * 100
    FROM generate_series(1, 100) as i;

COMMIT;