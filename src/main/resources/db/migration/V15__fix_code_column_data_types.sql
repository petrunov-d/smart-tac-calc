ALTER TABLE PUBLIC.train_types
    ALTER COLUMN code TYPE TEXT;

ALTER TABLE PUBLIC.line_type
    ALTER COLUMN code TYPE TEXT;

ALTER TABLE PUBLIC.service
    ALTER COLUMN code TYPE TEXT;

ALTER TABLE PUBLIC.tax_for_services_per_train
    ALTER COLUMN code_of_service TYPE TEXT;