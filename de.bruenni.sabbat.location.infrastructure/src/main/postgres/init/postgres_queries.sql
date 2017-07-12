-- TRUNCATE loc.activity;

insert into loc.activity (finished,
    "uuid",
    started,
    title,
    userid)
values (null,
    :aid,
    '2017-03-03T01:01:59Z',
    'some title',
    :uid);

insert into loc.activity (finished,
    "uuid",
    started,
    title,
    userid)
values (null,
    'activityid2',
    '2017-03-03T01:01:59Z',
    'some title',
    'username2');

    insert into loc.activity (finished,
    "uuid",
    started,
    title,
    userid)
values (null,
    'activityid3',
    '2017-03-03T01:01:59Z',
    'some title',
    'username3');

insert into activityrelation (
    activityid1,
    activityid2)
values (
    :activityid1,
    :activityid2)
;



-- SELECT currval(pg_get_serial_sequence('activity', 'id'));

-- select * from loc.activity where userid IN ('username1', 'username2');
EXPLAIN select * from loc.activity where uuid = 'activityid1';
select * from loc.activityrelation ;
select * from loc.activity ORDER BY started DESC;

EXPLAIN select a.uuid,
        a.userid,
        b.uuid,
        b.userid
        from loc.activity as a
inner join loc.activityrelation as ar ON a.id = ar.activityid2
inner join loc.activity as b ON ar.activityid1 = b.id
where a.uuid = :activityid
UNION
select a.uuid,
        a.userid,
        b.uuid,
        b.userid
        from loc.activity as a
inner join loc.activityrelation as ar ON a.id = ar.activityid1
inner join loc.activity as b ON ar.activityid2 = b.id
where a.uuid = :activityid;

select activity0_.id as id1_0_1_, activity0_.finished as finished2_0_1_, activity0_.started as started3_0_1_, activity0_.title as title4_0_1_, activity0_.userid as userid5_0_1_, activity0_.uuid as uuid6_0_1_, domaineven1_.aggregateid as aggregat4_2_3_, domaineven1_.id as id1_2_3_, domaineven1_.id as id1_2_0_, domaineven1_.aggregateid as aggregat4_2_0_, domaineven1_.document as document2_2_0_, domaineven1_.typeid as typeid3_2_0_ from loc.activity activity0_ left outer join loc.domainevents domaineven1_ on activity0_.id=domaineven1_.aggregateid where activity0_.id=66;
    domainevents d;

select ac."id",
ac.title,
ac.started,
de.DTYPE  from loc.activity as ac
LEFT OUTER join loc.domainevents as de ON de.aggregateid = ac.id AND de.DTYPE = 1;
--where ac."id" IN (160, 151, 152, 150);

--select activity that are not started yet

select ac."id",
ac.title,
ac.started,
de.DTYPE  from loc.activity as ac
inner join loc.domainevents as de ON de.aggregateid = ac.id

--select activitis started but not stopped yet to react to activitystartedevent and find relating activities in the group

truncate table loc.activity cascade ;


UPDATE loc.domainevents
SET aggregateid = 160
where id = 79;

select ac.id, de.dtype as dt, ac.started from loc.activity as ac
inner join loc.domainevents as de ON de.aggregateid = ac.id
ORDER BY ac.started DESC;

select aggregateid, MAX(created), Count(dtype) as "count", MAX(dtype) from loc.domainevents
GROUP BY aggregateid
order by "count" DESC;

select a.id,
a.title,
de.created,
de.id as "deId",
de.dtype as "de.dtype",
de2.id as "de2Id",
de2.dtype as "de2.dtype"
from loc.activity as a
INNER JOIN loc.domainevents as de ON de.aggregateid = a.id AND de.dtype = 1
LEFT JOIN loc.domainevents as de2 ON de2.aggregateid = a.id AND de2.dtype = 5 AND de.created < de2.created    -- find all stooped activities and use the complementary set
where de2.id IS NULL
ORDER BY a.started DESC;

select a.id,
a.title,
de.created,
de.id as "deId",
de.dtype as "de.dtype"
from loc.activity as a
INNER JOIN loc.domainevents as de ON de.aggregateid = a.id
--where de2.id IS NULL
ORDER BY a.started DESC;

select a.id,
a.title,
de.created,
de.id as "deId",
de.dtype as "de.dtype",
de2.id as "de2Id",
de2.dtype as "de2.dtype"
from loc.activity as a
INNER JOIN loc.domainevents as de ON de.aggregateid = a.id AND de.dtype = 1
LEFT JOIN loc.domainevents as de2 ON de2.aggregateid = a.id AND de2.dtype = 5-- find all stooped activities and use the complementary set
--where de2.id IS NULL
ORDER BY a.started DESC;[object Object]


SELECT * FROM loc.activity as a " +
					"WHERE a.aggregateid = 461 " +
					" AND " +
					"  (SELECT dtype" +
					"	FROM a.domainEvents as de" +
					"	WHERE TYPE(de) IN (ActivityStartedEvent, ActivityStoppedEvent) AND de.createdOn = MAX(de.createdOn)

select a.uuid, a2.uuid from loc.activity as  a
inner join loc.activityrelation as r ON a."id" = r.activityid1 OR a."id" = r.activityid2
inner join loc.activity as a2 ON a2."id" = r.activityid1 OR a2."id" = r.activityid2
ORDER by a.started DESC

select * from loc.activity as  a
inner join loc.domainevents as d ON d.aggregateid = a."id"
ORDER by a.started DESC

truncate table loc.activity CASCADE;
commit;




select * from loc.activity;
select * from loc.activity ORDER BY started DESC;
select * from loc.activityrelation;
select * from loc.domainevents;