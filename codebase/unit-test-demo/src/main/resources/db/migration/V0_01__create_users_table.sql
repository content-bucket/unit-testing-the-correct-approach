CREATE SEQUENCE user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE TABLE users
(
    id         bigint  NOT NULL DEFAULT (cast(date_part('epoch', now()) as INT8) << 23) | (nextval('user_id_seq'::regclass) % 8388608),
    first_name varchar NULL,
    last_name  varchar NULL,
    email      varchar NOT NULL,
    "password" varchar NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE (email)
);
CREATE INDEX users_email_idx ON users (email);
