INSERT INTO provinces (name, revolt_risk)
VALUES ('Northmarch', 4.50);

INSERT INTO peasants (name, balance, province_id)
SELECT 'Aldwin of the Fen', 120.00, id
FROM provinces
WHERE name = 'Northmarch';

INSERT INTO peasants (name, balance, province_id)
SELECT 'Brunhilda Miller', 85.50, id
FROM provinces
WHERE name = 'Northmarch';
