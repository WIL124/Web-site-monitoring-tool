CREATE Schema if not exists monitoring;

drop table if exists browsers;
drop table if exists countries;
drop table if exists daysOfWeek;
drop table if exists pages;
drop table if exists time_of_day;
drop table if exists users;

create table if not exists browsers
(
    id         bigserial not null primary key,
    name       text      not null,
    count      bigint    not null,
    created_at timestamp not null
);
create table if not exists countries
(
    id         bigserial not null primary key,
    name       text      not null,
    count      bigint    not null,
    created_at timestamp not null
);
create table if not exists days_of_week
(
    id         bigserial not null primary key,
    name       text      not null,
    count      bigint    not null,
    created_at timestamp not null
);
create table if not exists pages
(
    id         bigserial not null primary key,
    name       text      not null,
    count      bigint    not null,
    created_at timestamp not null
);
create table if not exists time_of_day
(
    id         bigserial not null primary key,
    name       text      not null,
    count      bigint    not null,
    created_at timestamp not null
);
create table if not exists users
(
    id         bigserial not null primary key,
    name       text      not null,
    count      bigint    not null,
    created_at timestamp not null
);