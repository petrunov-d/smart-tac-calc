ALTER TABLE PUBLIC.tax_per_train
    ADD COLUMN insert_time date not null default now();