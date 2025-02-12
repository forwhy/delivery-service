CREATE TABLE parcel (
     id         SERIAL PRIMARY KEY,
     name       VARCHAR(255) NOT NULL,
     symbol     CHAR(1),
     form       VARCHAR(1000)
);

CREATE UNIQUE INDEX parcel_name_idx ON prcls.parcel (name);

INSERT INTO parcel (name, symbol, form)
VALUES ('Посылка Тип 1', '1', '1'),
       ('Посылка Тип 2', '2', '22'),
       ('Посылка Тип 3', '3', '333'),
       ('Посылка Тип 4', '4', '4444'),
       ('Посылка Тип 5', '5', '55555'),
       ('Посылка Тип 6', '6', '666%n666%n'),
       ('Посылка Тип 7', '7', '777%7777'),
       ('Посылка Тип 8', '8', '8888%n8888'),
       ('Посылка Тип 9', '9', '999%n999%n999');
