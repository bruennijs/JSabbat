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
EXPLAIN select * from loc.activity where uuid = 'activityid3';
select * from loc.activityrelation ;

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