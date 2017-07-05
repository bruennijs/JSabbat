CREATE KEYSPACE sabbatlocation WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 2};

use sabbatlocation;

CREATE TABLE coordinates_by_user (
	userid varchar,    
    activityid varchar,    
    captured timestamp,    
    --bucketyearday tuple<int, int>,	-- to keep all coordinates of same day together
    long_lat tuple<decimal, decimal>,
    --velocity decimal,	-- can be null if calculated in batch
    deltaS decimal,
    PRIMARY KEY ((userid), activityid, captured)
) WITH CLUSTERING ORDER BY (activityid ASC, captured DESC);

CREATE TABLE coordinates_by_group (
    groupid varchar,
    captured timestamp,
    activityid varchar,
    userid varchar,
    long_lat tuple<decimal, decimal>,
    velocity decimal,
    deltaS decimal,
    PRIMARY KEY ((groupid), captured)
) WITH CLUSTERING ORDER BY (captured DESC);
--AND default_time_to_live=86400;	--one day time to live
--AND default_time_to_live=30;

--CREATE INDEX IF NOT EXISTS useridIndex on coordinates_by_group (activityid);

--DROP KEYSPACE sabbatlocation;