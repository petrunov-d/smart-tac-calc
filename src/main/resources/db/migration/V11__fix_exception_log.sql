drop table public.exception_log;

CREATE TABLE PUBLIC.exception_log
(
    id             UUID PRIMARY KEY NOT NULL,
    timestamp      TIMESTAMP,
    exception_name VARCHAR,
    method_name    VARCHAR,
    stacktrace     TEXT
);
