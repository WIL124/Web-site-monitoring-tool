drop table main cascade;
create table if not exists main
(
    id        BIGSERIAL PRIMARY KEY,
    timestamp timestamp NOT NULL,
    users     BIGINT    NOT NULL,
    visits    BIGINT    NOT NULL
);
create table language
(
    id BIGSERIAL REFERENCES main (id)
)