drop table if exists tbl_itrust_aud;

create table tbl_itrust_aud (
  audit_id int(8) not null auto_increment,
  txn_type varchar(6),
  tbl_name varchar(256),
  record_id varchar(32),
  txn_author_id varchar(32),
  txn_date timestamp not null default current_timestamp,
  txn_field longtext,
  txn_detail_bef longtext,
  txn_detail_aft longtext,
  primary key (audit_id)
);