--use sabbatlocation;


-- USE CASE: SELECT coordinates of one user for entire activity
select * from coordinates_by_user where userid = 'userid1' AND activityid = 'activityid1';

--------------------------------------------------------------------------
-- USE CASE: current velocity of all users of a group
select * from coordinates_by_group where groupid = 'ol' AND bucket captured >= '2017-6-28T23:00:00Z'
	ORDER BY captured DESC;

------------------------------------------------
-- USE CASE: average velocity, entire distance since start, velocity of each activity of all users of a group
select activityid, velocityAvg, velocity, distance from coordinates_by_group where groupid = 'ol' AND bucket = '2017-6-28T23:00:00Z';

-------------------------------------------------
-- USE CASE: current position of each activity of all users of a group to calculate distances between them
select bucket, activityid, captured, longitude, latitude from coordinates_by_group where groupid = 'ol' AND bucket = '2017-6-28T22:59:00Z'; 

-- USE CASE: calculate ride time of one activity (cause we don't have group by) 
-- not so important cause postgres keeps start time of activity but we can calculate min max captured values by activity ID
select MIN(captured), MAX(captured) from coordinates_by_user where userid = 'bruenni' AND activityid = 'br_activity1';


----------- OLD style
-- USE CASE: SELECT last 2 coordinates of one activity to calculate current velocity BUT LIMIT is expensive cause many stable smzst be searched 
-- (http://thelastpickle.com/blog/2017/03/07/The-limit-clause-in-cassandra-might-not-work-as-you-think.html)
select * from coordinates_by_user where userid = 'bruenni' AND activityid = 'br_activity1'
	ORDER BY activityid ASC, captured DESC
	LIMIT 2;	
-- alternative use clustering column range (last 5 minutes for example)
select * from coordinates_by_user where userid = 'bruenni' AND activityid = 'br_activity1' AND captured >= '2017-6-28T23:16:00Z'
	ORDER BY activityid ASC, captured DESC;


-- USE CASE: average velocity, current distance & velocity of each activity of all users of a group
select SUM(deltaS)  as distance, MIN(captured), MAX(captured) from coordinates_by_user where userid = 'bruenni' AND activityid = 'br_activity1';
select SUM(deltaS)  as distance, MIN(captured), MAX(captured) from coordinates_by_user where userid = 'anmema' AND activityid = 'an_activity1';
-- NOTE: support only in CQL 3.4.3 (http://cassandra.apache.org/doc/latest/cql/changes.html#id1)
--select SUM(deltaS) as distance from coordinates_by_user
--GROUP BY userid, activityid;