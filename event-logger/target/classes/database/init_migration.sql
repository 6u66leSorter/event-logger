CREATE TABLE IF NOT EXISTS events
(
    id                   bigserial NOT NULL PRIMARY KEY,
    name                 varchar   NOT NULL,
    date                 timestamp NOT NULL,
    address              varchar   NOT NULL,
    authorized           varchar   NOT NULL
);
