INSERT INTO singer (name)
VALUES ('Madonna'),
       ('Michael Jackson'),
       ('Billie Eilish'),
       ('Metallica');
commit;

INSERT INTO company (name)
VALUES ('Black Company'),
       ('The best company'),
       ('Europe label'),
       ('Imagine label');
commit;


INSERT INTO recording (song_code, title, version, release_time, singer_id)
VALUES ('SN 123', 'Frozen', '1', '1008-01-15', 1);

INSERT INTO recording (song_code, title, version, release_time, singer_id)
VALUES ('SN 125', 'Earth songs', '1', '1982-01-15', 2);

INSERT INTO recording (song_code, title, version, release_time, singer_id)
VALUES ('SN 126', 'Bad Guy', '1', '2019-01-15', 3);

INSERT INTO recording (song_code, title, version, release_time, singer_id)
VALUES ('SN 127', 'Nothing else matters', '1', '1991-01-15', 4);

INSERT INTO copyright (royalty, period_start, period_end, recording_id, company_id)
VALUES (0.99, '2022-01-01', '2022-03-31', 1, 1),
       (0.99, '2022-04-01', '2022-12-31', 1, 2),
       (0.99, '2022-01-01', '2022-12-31', 2, 3),
       (0.99, '2022-01-01', '2023-12-31', 2, 1)
commit;

