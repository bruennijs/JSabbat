-- https://www.postgresql.org/docs/9.6/static/role-attributes.html
-- do this with connection to server with user 'postgres' having
-- right to create roles
-- command line
-- psql -h localhost -U postgres -f de.bruenni.sabbat.location.infrastructure/src/main/postgres/init/sabbat_init_db.sql

BEGIN;
--DROP ROLE sabbat;
CREATE ROLE sabbat WITH LOGIN INHERIT UNENCRYPTED PASSWORD 'sabbat#2017';

--DROP ROLE data_rw;
CREATE ROLE data_rw NOINHERIT;

GRANT data_rw TO sabbat;
COMMIT;

-- SET ROLE postgres;
--DROP DATABASE sabbat;
CREATE DATABASE sabbat OWNER postgres;
