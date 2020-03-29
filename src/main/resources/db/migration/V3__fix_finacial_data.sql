DROP TABLE PUBLIC.financial_data;

CREATE TABLE PUBLIC.financial_data
(
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    direct_cost_description TEXT,
    direct_cost_value       INTEGER
);

