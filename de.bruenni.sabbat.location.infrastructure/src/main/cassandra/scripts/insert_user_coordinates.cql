--use sabbatlocation;

INSERT into coordinates_by_user (userid, captured, activityid, long_lat, deltaS) VALUES ('bruenni', '2017-6-28T23:00:00Z', 'br_activity1', (23.11, 34.65), 0.34);
INSERT into coordinates_by_user (userid, captured, activityid, long_lat, deltaS) VALUES ('bruenni', '2017-6-28T23:15:00Z', 'br_activity1', (24.07, 36.00), 0.32);
INSERT into coordinates_by_user (userid, captured, activityid, long_lat, deltaS) VALUES ('anmema', '2017-6-28T23:14:30Z', 'an_activity1', (25.07, 35.00), 0.21);
INSERT into coordinates_by_user (userid, captured, activityid, long_lat) VALUES ('anmema', '2017-6-28T22:59:15Z', 'an_activity1', (25.07, 35.00));
INSERT into coordinates_by_user (userid, captured, activityid, long_lat, deltaS) VALUES ('bruenni', '2017-6-28T22:59:00Z', 'br_activity1', (25.07, 35.00), 0.21);
INSERT into coordinates_by_user (userid, captured, activityid, long_lat, deltaS) VALUES ('anmema', '2017-6-28T23:15:30Z', 'an_activity1', (25.07, 39.00), 0.451);
INSERT into coordinates_by_user (userid, captured, activityid, long_lat) VALUES ('anmema', '2017-6-28T22:58:30Z', 'an_activity1', (25.07, 39.00));
INSERT into coordinates_by_user (userid, captured, activityid, long_lat) VALUES ('bruenni', '2017-6-28T23:15:20Z', 'br_activity1', (25.07, 35.00));
INSERT into coordinates_by_user (userid, captured, activityid, long_lat) VALUES ('bruenni', '2017-6-28T23:15:40Z', 'br_activity1', (25.07, 35.00));
INSERT into coordinates_by_user (userid, captured, activityid, long_lat) VALUES ('bruenni', '2017-6-28T23:16:00Z', 'br_activity1', (25.07, 35.00));
INSERT into coordinates_by_user (userid, captured, activityid, long_lat) VALUES ('bruenni', '2017-6-28T23:16:20Z', 'br_activity1', (25.07, 35.00));

INSERT into coordinates_by_group (groupid, captured, activityid, userid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T23:00:00Z', 'br_activity1', 'bruenni', (23.11, 34.65), 12.2, 0.12);
INSERT into coordinates_by_group (groupid, captured, activityid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T23:00:20.000Z', 'br_activity1', (23.11, 34.65), 16.2, 0.13);
INSERT into coordinates_by_group (groupid, captured, activityid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T23:00:40Z', 'br_activity1', (23.11, 34.65), 32.2, 0.4);
INSERT into coordinates_by_group (groupid, captured, activityid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T22:59:00Z', 'br_activity1', (23.11, 34.65), 0.0, 0.0);
INSERT into coordinates_by_group (groupid, captured, activityid, userid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T22:59:40Z', 'an_activity1', 'anmema', (23.11, 34.65), 0.0, 0.0);
INSERT into coordinates_by_group (groupid, captured, activityid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T23:00:00Z', 'an_activity1', (23.11, 34.65), 25.32, 0.2);
INSERT into coordinates_by_group (groupid, captured, activityid, long_lat, velocity, deltaS) VALUES ('ol', '2017-6-28T23:00:20Z', 'an_activity1', (23.11, 34.65), 27.11, 0.31);
