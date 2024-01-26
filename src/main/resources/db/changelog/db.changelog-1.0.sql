--liquibase formatted sql

--changeset vladislav:1
CREATE TABLE if not exists weather_summary
(
    id      serial PRIMARY KEY,
    date    TIMESTAMP   NOT NULL,
    address VARCHAR(50) NOT NULL,
    avtemp  float,
    avhum   float,
    avwind  float

);

CREATE TABLE if not exists weather_during_day
(
    id        serial PRIMARY KEY,
    summaryid serial,
    hour      int,
    temp      float,
    hum       float,
    wind      float,
    FOREIGN KEY (summaryid)
        REFERENCES weather_summary (id)

);