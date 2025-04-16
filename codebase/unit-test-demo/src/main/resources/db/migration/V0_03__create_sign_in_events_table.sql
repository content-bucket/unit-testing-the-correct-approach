CREATE SEQUENCE user_sign_in_event_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE TABLE user_sign_in_event
(
    id      bigint  NOT NULL DEFAULT (cast(date_part('epoch', now()) as INT8) << 23) | (nextval('user_id_seq'::regclass) % 8388608),
    type    varchar NOT NULL,
    user_id bigint  NOT NULL,
    email   varchar NOT NULL,
    CONSTRAINT users_sign_in_event_pk PRIMARY KEY (id)
);

CREATE INDEX users_sign_in_event_email_idx ON users (email);
