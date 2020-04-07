DROP TABLE PUBLIC.traffic_data;

CREATE TABLE PUBLIC.traffic_data
(
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    direct_cost_description TEXT,
    direct_cost_value       NUMERIC,
    code                    VARCHAR,
    year                    INTEGER
);

