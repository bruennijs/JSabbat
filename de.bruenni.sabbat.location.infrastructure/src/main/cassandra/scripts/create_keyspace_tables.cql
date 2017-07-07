CREATE KEYSPACE sabbatlocation WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 2};

use sabbatlocation;

CREATE TABLE coordinates_by_user (
	userid varchar,    
    activityid varchar,    
    captured timestamp,    
    --bucketyearday tuple<int, int>,	-- to keep all coordinates of same day together
    longitude decimal,
    latitude decimal,
    deltaS decimal, -- is calculated in batch after all coordinates are present in
                    -- maybe calculated more accurate via routing service (not just euclidean distance) 
    PRIMARY KEY ((userid), activityid, captured)
) WITH CLUSTERING ORDER BY (activityid ASC, captured DESC);

-- REAL TIME view (calculated by CEP enfine) & SERVING LAYER
-- Can be calculated from batch layer (coordinates_by_user)
-- Moreover it can be used to calculate euclidean distances between user activities
CREATE TABLE coordinates_by_group (
    groupid varchar,
    bucket timestamp,	-- granularity must is part of the business logic (multiple rows per bucket per activity possible)
    activityid varchar,    
    userid varchar,    
    captured timestamp,
    longitude decimal,
    latitude decimal,       
    distance decimal,	-- entire distance since start of the activity
    velocity decimal,	-- current velocity: deltaS / deltaT (t of this coordinate - t of last coordinate)
    velocityAvg decimal,	-- current average velocity: calulated by CEP engine
    PRIMARY KEY ((groupid), bucket, activityid)
) WITH CLUSTERING ORDER BY (bucket DESC, activityid ASC)
AND default_time_to_live=86400;	--one day time to live
--AND default_time_to_live=30;

--CREATE INDEX IF NOT EXISTS useridIndex on coordinates_by_group (activityid);

DROP KEYSPACE sabbatlocation;