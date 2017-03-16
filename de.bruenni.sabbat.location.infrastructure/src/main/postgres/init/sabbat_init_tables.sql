-- Command line
-- psql -h localhost -U postgres -d sabbat -f de.bruenni.sabbat.location.infrastructure/src/main/postgres/init/sabbat_init_tables.sql

-- Schema containing all location domain specific tables

BEGIN;
    CREATE SCHEMA IF NOT EXISTS loc;

    CREATE TYPE domain_event_type AS ENUM ('activity');

    CREATE TABLE IF NOT EXISTS loc.activity (
        id SERIAL PRIMARY KEY,
        uuid VARCHAR(40) not null UNIQUE,    -- activity uuid created by clients (Oruxmaps e.g.)
        userid VARCHAR(255) not null,
        title VARCHAR(255) not null,
        started TIMESTAMP not null,
        finished TIMESTAMP null -- if null means activity is still active, not stopped yet
    );

    ---------------------------------------

    CREATE TABLE IF NOT EXISTS loc.activityrelation (
        id SERIAL PRIMARY KEY,
        activityid1 SERIAL not null REFERENCES loc.activity (id) ON DELETE CASCADE,
        activityid2 SERIAL not null REFERENCES loc.activity (id) ON DELETE CASCADE
    );

    -- time series events coming in from CEP with new distances between activities --
    CREATE TABLE IF NOT EXISTS loc.activityrelationevents (
        id SERIAL PRIMARY KEY,
        aggregateid SERIAL not null REFERENCES loc.activityrelation (id) ON DELETE CASCADE,
        document JSONB not null
    );

        -- -- DOMAIN events --
    CREATE TABLE IF NOT EXISTS loc.domainevents (
        id SERIAL PRIMARY KEY,
        aggregateid SERIAL not null,
        type domain_event_type not null,
        document JSONB not null
        -- FOREIGN KEY (aggregateid) REFERENCES loc.activity (id) ON DELETE CASCADE,
        -- FOREIGN KEY (aggregateid) REFERENCES loc.activityrelation (id) ON DELETE CASCADE
    );

    --- GRANT region ----
    GRANT USAGE ON SCHEMA loc TO data_rw;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA loc TO data_rw;
    GRANT ALL ON ALL SEQUENCES IN SCHEMA loc TO data_rw;
COMMIT;

    --DROP table loc.activity;
    --DROP table loc.activityrelation;
    --DROP table loc.events;