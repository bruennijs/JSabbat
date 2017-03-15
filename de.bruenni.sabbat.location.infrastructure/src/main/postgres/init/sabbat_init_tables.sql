-- Command line
-- psql -h localhost -U postgres -d sabbat -f de.bruenni.sabbat.location.infrastructure/src/main/postgres/init/sabbat_init_tables.sql

-- Schema containing all location domain specific tables

BEGIN;
    CREATE SCHEMA IF NOT EXISTS loc;

    CREATE TABLE IF NOT EXISTS loc.activity (
        id SERIAL PRIMARY KEY,
        uuid VARCHAR(40) UNIQUE,
        userid VARCHAR(255) not null,
        title VARCHAR(255),
        started TIMESTAMP not null,
        finished TIMESTAMP null
    );

    GRANT ALL ON ALL SEQUENCES IN SCHEMA loc TO data_rw;

    ---------------------------------------

    CREATE TABLE IF NOT EXISTS loc.activityrelation (
        id SERIAL PRIMARY KEY,
        activityid1 SERIAL not null REFERENCES loc.activity (id) ON DELETE CASCADE,
        activityid2 SERIAL not null REFERENCES loc.activity (id) ON DELETE CASCADE
    );

    -- DOMAIN events --
    CREATE TABLE IF NOT EXISTS loc.activityrelationevents (
        id SERIAL PRIMARY KEY,
        aggregateid SERIAL not null REFERENCES loc.activityrelation (id) ON DELETE CASCADE,
        event_data JSONB not null
    );

    --- GRANT region ----
    GRANT USAGE ON SCHEMA loc TO data_rw;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA loc TO data_rw;
COMMIT;

    --DROP table loc.activity;
    --DROP table loc.activityrelation;
    --DROP table loc.events;
