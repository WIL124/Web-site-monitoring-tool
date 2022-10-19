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
create table if not exists dayOfWeeks
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
create table if not exists timeOfDay
(
    id         bigserial not null primary key,
    int        smallint  not null,
    count      bigint    not null,
    created_at timestamp not null
);