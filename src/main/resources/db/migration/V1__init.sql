 -- TRAIN_TYPES
CREATE TABLE PUBLIC.train_types
  (
     id   BIGSERIAL PRIMARY KEY NOT NULL,
     code INTEGER,
     name TEXT,
     year INTEGER,
     unique (year, name)
  );

-- UNIT_PRICE
CREATE TABLE PUBLIC.unit_price
  (
     id         BIGSERIAL PRIMARY KEY NOT NULL,
     code       INTEGER,
     name       TEXT,
     measure    TEXT,
     unit_price NUMERIC,
     year INTEGER,
     unique (year, code, name)
  );

-- LINE_TYPES
CREATE TABLE PUBLIC.line_type
  (
     id        BIGSERIAL PRIMARY KEY NOT NULL,
     line_type TEXT,
     name      TEXT,
     year INTEGER,
     unique (year, name, line_type)
  );

-- RAIL_STATIONS
CREATE TABLE PUBLIC.rail_station
  (
     id             BIGSERIAL PRIMARY KEY NOT NULL,
     line_number    INTEGER,
     station        TEXT,
     type           TEXT,
     is_key_station BOOLEAN,
     country TEXT,
     year INTEGER,
     unique (year, station)
  );

-- STRATEGIC_COEFFICIENTS
CREATE TABLE PUBLIC.strategic_coefficient
  (
     id          BIGSERIAL PRIMARY KEY NOT NULL,
     code        INTEGER,
     name        TEXT,
     coefficient NUMERIC,
     year INTEGER,
     unique (year, name, code)
  );

-- MAIN_SERVICE
CREATE TABLE PUBLIC.service
  (
     id         BIGSERIAL PRIMARY KEY NOT NULL,
     code       INTEGER,
     metric     TEXT,
     unit_price NUMERIC,
     name       TEXT,
     type       TEXT,
     year INTEGER,
     unique (year, name, code)
  );

CREATE TABLE PUBLIC.line_number
  (
     id          BIGSERIAL PRIMARY KEY NOT NULL,
     line_number INTEGER,
     description TEXT,
     year INTEGER,
     unique (year,line_number)
  );

CREATE TABLE PUBLIC.tax_for_services_per_train
  (
     id              BIGSERIAL PRIMARY KEY NOT NULL,
     train_number    INTEGER,
     station         TEXT,
     code_of_service INTEGER,
     tax             INTEGER,
     number          INTEGER,
     year INTEGER
  );

CREATE TABLE PUBLIC.financial_data
  (
     id               BIGSERIAL PRIMARY KEY NOT NULL,
     line_number      INTEGER,
     line_type        TEXT,
     line_length      INTEGER,
     is_electrified   TEXT,
     train_type       INTEGER,
     account_group_1  INTEGER,
     account_group_21 INTEGER,
     account_group_40 INTEGER,
     account_group_42 INTEGER,
     account_group_43 INTEGER,
     account_group_44 INTEGER,
     account_group_45 INTEGER,
     account_group_46 INTEGER,
     account_group_47 INTEGER,
     account_group_48 INTEGER,
     account_group_49 INTEGER,
     year INTEGER
  );

CREATE TABLE PUBLIC.traffic_data
  (
     id                                           BIGSERIAL PRIMARY KEY NOT NULL,
     line_number                                  INTEGER,
     rank                                         TEXT,
     line_length                                  INTEGER,
     is_electrified                               TEXT,
     train_type                                   INTEGER,
     freight_traffic_train_kilometers             INTEGER,
     freight_traffic_train_bruto_ton_kilometers   INTEGER,
     passenger_traffic_train_kilometers           INTEGER,
     passenger_traffic_train_bruto_ton_kilometers INTEGER,
     year INTEGER
  );

  create TABLE PUBLIC.section
(
    id                          BIGSERIAL PRIMARY KEY NOT NULL,
    line_number                 INTEGER,
    line_type                   TEXT,
    first_key_point             TEXT,
    last_key_point              TEXT,
    kilometers_between_stations NUMERIC,
    is_electrified              BOOLEAN,
    unit_price                  NUMERIC,
    year                        INTEGER
);

create TABLE PUBLIC.sub_section
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    kilometers      NUMERIC,
    non_key_station TEXT,
    section_fk      BIGSERIAL,
    year            INTEGER
);

alter table PUBLIC.sub_section
    add CONSTRAINT fk_section FOREIGN KEY (section_fk) REFERENCES PUBLIC.section (id);

create TABLE PUBLIC.service_charges_per_train
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    train_number    INTEGER,
    service_count   INTEGER,
    rail_station_id INTEGER REFERENCES PUBLIC.rail_station (id),
    service_id      INTEGER REFERENCES PUBLIC.service (id),
    year            INTEGER
);