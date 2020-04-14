-- Financial Data
ALTER TABLE PUBLIC.financial_data
    ADD UNIQUE (code, year);

-- Line Numbers
ALTER TABLE PUBLIC.line_number
    ADD UNIQUE (line_number, year);

-- Line Types
ALTER TABLE PUBLIC.line_type
    ADD UNIQUE (line_type, year);

ALTER TABLE PUBLIC.line_type
    ADD UNIQUE (code, year);

-- Rail Stations
ALTER TABLE PUBLIC.rail_station
    ADD UNIQUE (station, country, year);

-- Section
ALTER TABLE PUBLIC.section
    ADD UNIQUE (first_key_point, last_key_point, line_type, line_number, year);

-- Service
ALTER TABLE PUBLIC.service
    ADD UNIQUE (code, year);

ALTER TABLE PUBLIC.service
    ADD UNIQUE (name, type, year);

-- Strategic Coefficients
ALTER TABLE PUBLIC.strategic_coefficient
    ADD UNIQUE (code, year);

-- Traffic Data
ALTER TABLE PUBLIC.traffic_data
    ADD UNIQUE (code, year);

-- Train Type
ALTER TABLE PUBLIC.train_types
    ADD UNIQUE (code, year);

-- Unit Price
ALTER TABLE PUBLIC.unit_price
    ADD UNIQUE (code, year);