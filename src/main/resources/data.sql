INSERT INTO poi_person
(id, age, first_name, last_name)
VALUES
(1 , 60, 'Петр',   'Сидоров'),
(2 , 70, 'Иван',   'Петров'),
(3 , 60, 'Петр1',  'Сидоров1'),
(4 , 60, 'Петр2',  'Сидоров2'),
(5 , 60, 'Петр3',  'Сидоров3'),
(6 , 60, 'Петр4',  'Сидоров4'),
(7 , 60, 'Петр5',  'Сидоров5'),
(8 , 60, 'Петр6',  'Сидоров6'),
(9 , 60, 'Петр7',  'Сидоров7'),
(10, 60, 'Петр8',  'Сидоров8'),
(11, 60, 'Петр9',  'Сидоров9'),
(12, 60, 'Петр10', 'Сидоров10'),
(13, 60, 'Петр11', 'Сидоров11'),
(14, 60, 'Петр12', 'Сидоров12');

alter sequence poi_person_seq restart with 52;

INSERT INTO poi_house
(id, address)
VALUES
(1, 'Дом1'),
(2, 'Дом2'),
(3, 'Дом3');

INSERT INTO poi_ownership
(id, person_id, house_id, percent, percent1, percent2, percent3, percent4, percent5, percent6, percent7, percent8, percent9, name1, name2, name3, name4, name5, name6, name7, name8, name9, nick1, nick2, nick3, nick4, nick5)
VALUES
(1 , 1, 1, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(2 , 1, 2, 15, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(3 , 2, 1, 70, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(4 , 2, 2, 15, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(5 , 2, 3, 60, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(6 , 2, 3, 30, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(7 , 1, 1, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(8 , 1, 2, 15, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(9 , 2, 1, 70, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5'),
(10, 2, 2, 15, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7', 'name8', 'name9', 'nick1', 'nick2', 'nick3', 'nick4', 'nick5');


