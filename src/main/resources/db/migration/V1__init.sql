create table device_groups
(
    id   bigserial primary key,
    name varchar(255)
);

create table devices
(
    id               bigserial primary key,
    name             varchar(255),
    imei             varchar(255) unique not null,
    device_groups_id bigserial references device_groups (id)
);

insert into device_groups (name)
values ('group1'),
       ('group2'),
       ('group3'),
       ('group4');


insert into devices (name, device_groups_id, imei)
values ('gps1', 1, '22www'),
       ('gps2', 2, '44qwe'),
       ('gps3', 3, 'wwww'),
       ('gps4', 4, 'zz111'),
       ('gps5', 1, 'aaa333'),
       ('gps6', 2, 'qqq22'),
       ('gps7', 3, 'wweerr22');

-- create table geoposition
-- (
--     id         bigserial primary key,
--     time_track timestamp,
--     lon DOUBLE,
--     lat DOUBLE,
--     alt DOUBLE,
--     speed DOUBLE,
--     direction  NUMERIC
-- )


