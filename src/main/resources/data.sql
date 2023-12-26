INSERT INTO poi_person
(id, age, first_name, last_name)
VALUES
(1, 60, 'Петр', 'Сидоров'),
(2, 70, 'Иван', 'Петров');

alter sequence poi_person_seq restart with 52;

INSERT INTO poi_house
(id, address)
VALUES
(1, 'Дом1'),
(2, 'Дом2'),
(3, 'Дом3');

INSERT INTO poi_ownership
(id, person_id, house_id, percent)
VALUES
(1, 1, 1, 10),
(2, 1, 2, 15),
(3, 2, 1, 70),
(4, 2, 2, 15),
(5, 2, 3, 60),
(6, 2, 3, 30);
