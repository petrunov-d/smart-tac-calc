-- Markup Coefficients
CREATE TABLE PUBLIC.markup_coefficient
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    code        TEXT                  NOT NULL,
    name        TEXT,
    coefficient NUMERIC,
    year        INTEGER,
    unique (year, code)
);

-- Carrier Company
CREATE TABLE PUBLIC.carrier_company
(
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    carrier_name      TEXT                  NOT NULL,
    locomotive_series TEXT,
    locomotive_type   VARCHAR,
    locomotive_weight NUMERIC,
    year              INTEGER
);