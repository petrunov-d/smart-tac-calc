-- Sections Stations
ALTER TABLE PUBLIC.section
    ADD UNIQUE (first_key_point, line_number, year);