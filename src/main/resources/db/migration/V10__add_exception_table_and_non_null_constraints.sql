-- EXCEPTION_LOG
CREATE TABLE PUBLIC.exception_log
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    timestamp      TIMESTAMP,
    exception_name VARCHAR,
    method_name    VARCHAR,
    stacktrace     TEXT
);

ALTER TABLE PUBLIC.train_types
    ALTER COLUMN code SET NOT NULL;
ALTER TABLE PUBLIC.train_types
    ALTER COLUMN year SET NOT NULL;
ALTER TABLE PUBLIC.train_types
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE PUBLIC.unit_price
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE PUBLIC.line_type
    ALTER COLUMN line_type SET NOT NULL;
ALTER TABLE PUBLIC.line_type
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE PUBLIC.rail_station
    ALTER COLUMN line_number SET NOT NULL;
ALTER TABLE PUBLIC.rail_station
    ALTER COLUMN station SET NOT NULL;
ALTER TABLE PUBLIC.rail_station
    ALTER COLUMN is_key_station SET NOT NULL;
ALTER TABLE PUBLIC.rail_station
    ALTER COLUMN country SET NOT NULL;

ALTER TABLE PUBLIC.strategic_coefficient
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE PUBLIC.service
    ALTER COLUMN code SET NOT NULL;
ALTER TABLE PUBLIC.service
    ALTER COLUMN name SET NOT NULL;
ALTER TABLE PUBLIC.service
    ALTER COLUMN type SET NOT NULL;

ALTER TABLE PUBLIC.line_number
    ALTER COLUMN line_number SET NOT NULL;

ALTER TABLE PUBLIC.tax_for_services_per_train
    ALTER COLUMN train_number SET NOT NULL;
ALTER TABLE PUBLIC.tax_for_services_per_train
    ALTER COLUMN station SET NOT NULL;
ALTER TABLE PUBLIC.tax_for_services_per_train
    ALTER COLUMN code_of_service SET NOT NULL;
ALTER TABLE PUBLIC.tax_for_services_per_train
    ALTER COLUMN tax SET NOT NULL;
ALTER TABLE PUBLIC.tax_for_services_per_train
    ALTER COLUMN number SET NOT NULL;

ALTER TABLE PUBLIC.section
    ALTER COLUMN line_number SET NOT NULL;
ALTER TABLE PUBLIC.section
    ALTER COLUMN line_type SET NOT NULL;
ALTER TABLE PUBLIC.section
    ALTER COLUMN first_key_point SET NOT NULL;
ALTER TABLE PUBLIC.section
    ALTER COLUMN last_key_point SET NOT NULL;
ALTER TABLE PUBLIC.section
    ALTER COLUMN kilometers_between_stations SET NOT NULL;
ALTER TABLE PUBLIC.section
    ALTER COLUMN is_electrified SET NOT NULL;

ALTER TABLE PUBLIC.sub_section
    ALTER COLUMN kilometers SET NOT NULL;
ALTER TABLE PUBLIC.sub_section
    ALTER COLUMN non_key_station SET NOT NULL;

ALTER TABLE PUBLIC.service_charges_per_train
    ALTER COLUMN train_number SET NOT NULL;
ALTER TABLE PUBLIC.service_charges_per_train
    ALTER COLUMN service_count SET NOT NULL;

ALTER TABLE PUBLIC.financial_data
    ALTER COLUMN direct_cost_description SET NOT NULL;
ALTER TABLE PUBLIC.financial_data
    ALTER COLUMN direct_cost_value SET NOT NULL;
ALTER TABLE PUBLIC.financial_data
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE PUBLIC.traffic_data
    ALTER COLUMN direct_cost_description SET NOT NULL;
ALTER TABLE PUBLIC.traffic_data
    ALTER COLUMN direct_cost_value SET NOT NULL;