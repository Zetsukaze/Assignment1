drop table if exists tbl_departments;

create table tbl_departments (
	id varchar (32) not null,
  dept_name varchar (100) not null unique,
  dept_desc varchar (200),
  version integer,
  primary key (id)
);

drop table if exists tbl_departments_aud;

create table tbl_departments_aud (
  id varchar (32) not null,
  rev int (11) not null,
  rev_type tinyint (4) default null,
  dept_name varchar (100),
  dept_desc varchar (200),
  version integer,
  primary key (id, rev),
  foreign key (rev) references tbl_audit_rev_info (id)
);