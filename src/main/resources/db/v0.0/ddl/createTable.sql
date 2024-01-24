CREATE TABLE if not exists public.house
(
    id          BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    uuid        uuid                  NOT NULL UNIQUE,
    area        VARCHAR               NOT NULL,
    country     VARCHAR(64)           NOT NULL,
    city        VARCHAR(64)           NOT NULL,
    street      VARCHAR(64)           NOT NULL,
    number      BIGINT                NOT NULL,
    create_date TIMESTAMP             NOT NULL
);

CREATE TABLE if not exists public.person
(
    id              BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    uuid            uuid                  NOT NULL UNIQUE,
    name            VARCHAR(64)           NOT NULL,
    surname         VARCHAR(64)           NOT NULL,
    sex             VARCHAR(64)           NOT NULL,
    passport_series VARCHAR(4)            NOT NULL,
    passport_number BIGINT                NOT NULL UNIQUE,
    create_date     TIMESTAMP             NOT NULL,
    update_date     TIMESTAMP             NOT NULL,
    house_id        BIGINT REFERENCES house (id)
);

CREATE TABLE if not exists public.person_house
(
    id        BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    person_id BIGINT REFERENCES person (id),
    house_id  BIGINT REFERENCES house (id)
);

