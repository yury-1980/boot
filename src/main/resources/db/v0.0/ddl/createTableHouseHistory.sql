DROP TYPE IF EXISTS person_type;
CREATE TYPE person_type AS ENUM ('OWNER', 'TENANT');

CREATE TABLE IF NOT EXISTS house_history
(
    id        BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    date      DATE                  NOT NULL,
    type      person_type           NOT NULL,
    house_id  BIGINT REFERENCES house (id) NOT NULL ,
    person_id BIGINT REFERENCES person (id) NOT NULL
);