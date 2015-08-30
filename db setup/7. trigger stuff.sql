use ryanlimwr;
drop trigger if exists insert_tbl_aa_subject;
drop trigger if exists insert_tbl_aa_subject_login;

DELIMITER $$

create trigger insert_tbl_aa_subject after insert on tbl_aa_subject
  for each row begin
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'FIRST_NAME', '', NEW.FIRST_NAME);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'EMAIL', '', NEW.EMAIL);
  end;

$$

create trigger insert_tbl_aa_subject_login after insert on tbl_aa_subject_login
  for each row begin
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'LOGIN_NAME', '', NEW.LOGIN_NAME);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'PASSWORD', '', NEW.PASSWORD);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'PASSWORD_CHANGED_DATE', '', NEW.PASSWORD_CHANGED_DATE);
  end;

$$
