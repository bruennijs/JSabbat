--use sabbatlocation;

INSERT into coordinates_by_user (userid, captured, activityid, longitude, latitude, deltaS) VALUES ('bruenni', '2017-6-28T23:00:00Z', 'br_activity1', 23.11, 34.65, 0.34);
INSERT into coordinates_by_user (userid, captured, activityid, longitude, latitude, deltaS) VALUES ('bruenni', '2017-6-28T23:15:00Z', 'br_activity1', 24.07, 36.00, 0.32);
INSERT into coordinates_by_user (userid, captured, activityid, longitude, latitude, deltaS) VALUES ('anmema', '2017-6-28T23:14:30Z', 'an_activity1', 25.07, 35.00, 0.21);
INSERT into coordinates_by_user (userid, captured, activityid, longitude, latitude) VALUES ('anmema', '2017-6-28T22:59:15Z', 'an_activity1', 25.07, 35.00);
INSERT into coordinates_by_user (userid, captured, activityid, longitude, latitude, deltaS) VALUES ('bruenni', '2017-6-28T22:59:00Z', 'br_activity1', 25.07, 35.00, 0.21);
INSERT into coordinates_by_user (userid, captured, activityid, longitude,latitude, deltaS) VALUES ('anmema', '2017-6-28T23:15:30Z', 'an_activity1', 25.07, 39.00, 0.451);
INSERT into coordinates_by_user (userid, captured, activityid, longitude,latitude) VALUES ('anmema', '2017-6-28T22:58:30Z', 'an_activity1', 25.07, 39.00);
INSERT into coordinates_by_user (userid, captured, activityid, longitude,latitude) VALUES ('bruenni', '2017-6-28T23:15:20Z', 'br_activity1', 25.07, 35.00);
INSERT into coordinates_by_user (userid, captured, activityid, longitude,latitude) VALUES ('bruenni', '2017-6-28T23:15:40Z', 'br_activity1', 25.07, 35.00);
INSERT into coordinates_by_user (userid, captured, activityid, longitude,latitude) VALUES ('bruenni', '2017-6-28T23:16:00Z', 'br_activity1', 25.07, 35.00);
INSERT into coordinates_by_user (userid, captured, activityid, longitude,latitude) VALUES ('bruenni', '2017-6-28T23:16:20Z', 'br_activity1', 25.07, 35.00);

INSERT into coordinates_by_group (groupid, captured, activityid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T22:59:00Z', 'br_activity1', 23.11, 34.65, 0.0, 27.1, '2017-6-28T22:59:00Z');
INSERT into coordinates_by_group (groupid, captured, activityid, userid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T23:00:00Z', 'br_activity1', 'bruenni', 23.11, 34.65, 12.2, 27.0, '2017-6-28T23:00:00Z');
INSERT into coordinates_by_group (groupid, captured, activityid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T23:00:20.000Z', 'br_activity1', 23.11, 34.65, 16.2, 27.0, '2017-6-28T23:00:00Z');
INSERT into coordinates_by_group (groupid, captured, activityid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T23:00:40Z', 'br_activity1', 23.11, 34.65, 32.2, 27.0, '2017-6-28T23:00:00Z');
INSERT into coordinates_by_group (groupid, captured, activityid, userid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T22:59:40Z', 'an_activity1', 'anmema', 23.11, 34.65, 0.0, 29.0, '2017-6-28T22:59:00Z');
INSERT into coordinates_by_group (groupid, captured, activityid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T23:00:00Z', 'an_activity1', 23.11, 34.65, 25.32, 29.1, '2017-6-28T23:00:00Z');
INSERT into coordinates_by_group (groupid, captured, activityid, longitude,latitude, velocity, velocityavg, bucket) VALUES ('ol', '2017-6-28T23:00:20Z', 'an_activity1', 23.11, 34.65, 27.11, 29.2, '2017-6-28T23:00:00Z');
