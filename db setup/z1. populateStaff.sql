truncate tbl_staff;
insert into tbl_staff values('1337-00', '00000', 'Testing Staff 1', 'ts1', 'ts1@corp.com.sg', 12345678, str_to_date('1337/01/30', '%Y/%m/%d'), str_to_date('2015/01/01', '%Y/%m/%d'), null, 'Gamer', null, null, 1);
insert into tbl_staff values('1337-01', '00001', 'Testing Staff 2', 'ts2', 'ts2@corp.com.sg', 99999999, str_to_date('1337/01/30', '%Y/%m/%d'), str_to_date('2015/01/01', '%Y/%m/%d'), null, 'Noob', '1337-00', null, 1);
insert into tbl_staff values('1337-02', '00002', 'Testing Staff 3', 'ts3', 'ts3@corp.com.sg', 88888888, str_to_date('1337/01/30', '%Y/%m/%d'), str_to_date('2015/01/01', '%Y/%m/%d'), null, 'Feeder', '1337-01', null, 1);
insert into tbl_staff values('1337-03', '00003', 'Testing Staff 4', 'ts4', 'ts4@corp.com.sg', 77777777, str_to_date('1337/01/30', '%Y/%m/%d'), str_to_date('2015/01/01', '%Y/%m/%d'), null, 'Ohai', '1337-02', null, 1);
insert into tbl_staff values('1337-04', '00004', 'Testing Staff 5', 'ts5', 'ts5@corp.com.sg', 66666666, str_to_date('1337/01/30', '%Y/%m/%d'), str_to_date('2015/01/01', '%Y/%m/%d'), null, 'Blah', '1337-03', null, 1);
select * from tbl_staff;
