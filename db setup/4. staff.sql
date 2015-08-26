drop table if exists tbl_staff;

create table tbl_staff (
	id varchar (32) not null,
  staff_no smallint (5) not null unique,
  staff_name varchar (200) not null,
  login_id varchar (15) not null unique,
  email varchar (27) not null unique,
  staff_phone mediumint (8),
  dob date,
  join_date date,
  dept varchar (100),
  designation varchar (100),
  ro_id varchar (32),
  picture blob (65535),
  version integer,
  primary key (id),
  foreign key (dept) references tbl_departments (id),
  foreign key (ro_id) references tbl_staff (id)
);

drop table if exists tbl_staff_aud;

create table tbl_staff_aud (
	id varchar (32) not null,
  rev int (11) not null,
  rev_type tinyint (4) default null,
  staff_no smallint (5),
  staff_name varchar (200),
  login_id varchar (15),
  email varchar (27),
  staff_phone mediumint (8),
  dob date,
  join_date date,
  dept varchar (100),
  designation varchar (100),
  ro_id varchar (32),
  picture mediumblob,
  version integer,
  primary key (id, rev),
  foreign key (rev) references tbl_audit_rev_info (id)
);