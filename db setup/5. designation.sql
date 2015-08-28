delete from tbl_code_int where codetype_id = 'designation';
delete from tbl_codetype where codetype_id = 'designation';

insert into tbl_codetype values('designation', 'Designation Titles', 'tbl_code_int', 'y', 'codetype_id', 'code_id', 'code_desc', 'code_seq', null, null, null, '', null, 'DEF-group-groupA', null, null, 'locale');

insert into tbl_code_int values('designation', 'Consultant', null, null, 'Consultant', 1, 'A', now(), null, '', now(), 'en');
insert into tbl_code_int values('designation', 'Senior Consultant', null, null, 'Senior Consultant', 2, 'A', now(), null, '', now(), 'en');
insert into tbl_code_int values('designation', 'Manager', null, null, 'Manager', 3, 'A', now(), null, '', now(), 'en');
insert into tbl_code_int values('designation', 'Senior Manager', null, null, 'Senior Manager', 4, 'A', now(), null, '', now(), 'en');
insert into tbl_code_int values('designation', 'Director', null, null, 'Director', 5, 'A', now(), null, '', now(), 'en');
