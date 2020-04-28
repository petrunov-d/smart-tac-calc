CREATE TABLE PUBLIC.tax_per_train
(
    id                                                      BIGSERIAL PRIMARY KEY NOT NULL,
    train_number                                            INTEGER               NOT NULL,
    train_type                                              VARCHAR               NOT NULL,
    is_electrified                                          BOOLEAN               NOT NULL,
    locomotive_series                                       VARCHAR,
    locomotive_weight                                       NUMERIC               NOT NULL,
    train_weight_without_locomotive                         NUMERIC               NOT NULL,
    total_train_weight                                      NUMERIC               NOT NULl,
    train_length                                            NUMERIC               NOT NULL,
    start_station                                           VARCHAR               NOT NULL,
    end_station                                             VARCHAR               NOT NULL,
    calendar_of_movement                                    TEXT,
    notes                                                   TEXT,
    kilometers_on_electrified_lines                         NUMERIC,
    kilometers_on_non_elecrified_highway_and_regional_lines NUMERIC,
    kilometers_on_non_electrified_local_lines               NUMERIC,
    tax                                                     NUMERIC               NOT NULL,
    correlation_id                                          UUID                  NOT NULL,
    strategic_coefficient                                   NUMERIC,
    year                                                    INTEGER               NOT NULL
);