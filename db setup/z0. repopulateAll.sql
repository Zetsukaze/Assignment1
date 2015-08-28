truncate tbl_staff;
delete from tbl_departments;

insert into tbl_departments (id, dept_name, dept_desc, version) values ('00000', 'HR', 'Human Resources', 0);
insert into tbl_departments (id, dept_name, dept_desc, version) values ('00001', 'Finance', 'Finance', 0);
insert into tbl_departments (id, dept_name, dept_desc, version) values ('00002', 'Legal', 'Legal', 0);
insert into tbl_departments (id, dept_name, dept_desc, version) values ('00003', 'IT', 'Information Technologies', 0);
insert into tbl_departments (id, dept_name, dept_desc, version) values ('00004', 'SEC', 'Securites', 0);

insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date, version) values('00000', '00000', 'Zetsukaze', 'zetsukaze', 'zetsukaze@corp.com.sg', str_to_date('1989/11/12', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'), 0);
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date, version) values('00001', '00001', 'Luo Guo Jing', 'luoguojing', 'luoguojing@corp.com.sg', str_to_date('1988/07/15', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'), 0);
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date, version) values('00002', '00002', 'Khairunnisa', 'khairunnisa', 'khairunnisa@corp.com.sg', str_to_date('1990/03/07', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'), 0);
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date, version) values('00003', '00003', 'Yuan Hui', 'yuanhuit', 'yuanhuit@corp.com.sg', str_to_date('1990/05/13', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'), 0);
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date, version) values('00004', '00004', 'Wee Yen Ian', 'weeyenian', 'weeyenian@corp.com.sg', str_to_date('1988/12/01', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'), 0);

select * from tbl_departments;
select * from tbl_staff;
