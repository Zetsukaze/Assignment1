truncate tbl_staff;
delete from tbl_departments;

insert into tbl_departments (id, dept_name, dept_desc) values ('00000', 'HR', 'Human Resources');
insert into tbl_departments (id, dept_name, dept_desc) values ('00001', 'Finance', 'Finance');
insert into tbl_departments (id, dept_name, dept_desc) values ('00002', 'Legal', 'Legal');
insert into tbl_departments (id, dept_name, dept_desc) values ('00003', 'IT', 'Information Technologies');
insert into tbl_departments (id, dept_name, dept_desc) values ('00004', 'SEC', 'Securites');

insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date) values('00000', '00000', 'Zetsukaze', 'zetsukaze', 'zetsukaze@corp.com.sg', str_to_date('1989/11/12', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'));
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date) values('00001', '00001', 'Luo Guo Jing', 'luoguojing', 'luoguojing@corp.com.sg', str_to_date('1988/07/15', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'));
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date) values('00002', '00002', 'Khairunnisa', 'khairunnisa', 'khairunnisa@corp.com.sg', str_to_date('1990/03/07', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'));
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date) values('00003', '00003', 'Yuan Hui', 'yuanhuit', 'yuanhuit@corp.com.sg', str_to_date('1990/05/13', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'));
insert into tbl_staff (id, staff_no, staff_name, login_id, email, dob, join_date) values('00004', '00004', 'Wee Yen Ian', 'weeyenian', 'weeyenian@corp.com.sg', str_to_date('1988/12/01', '%Y/%m/%d'), str_to_date('2015/08/11', '%Y/%m/%d'));

select * from tbl_departments;
select * from tbl_staff;
