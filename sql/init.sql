drop table if exists browsers;
drop table if exists countries;
drop table if exists daysOfWeek;
drop table if exists pages;
drop table if exists time_of_day;
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
    url        text      not null,
    count      bigint    not null,
    created_at timestamp not null
);
create table if not exists time_of_day
(
    id         bigserial not null primary key,
    hour        smallint  not null,
    count      bigint    not null,
    created_at timestamp not null
);