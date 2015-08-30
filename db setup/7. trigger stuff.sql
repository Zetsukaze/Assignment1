use ryanlimwr;
drop trigger if exists insert_tbl_aa_subject;
drop trigger if exists insert_tbl_aa_subject_login;
drop trigger if exists update_tbl_aa_subject;
drop trigger if exists update_tbl_aa_subject_login;
drop trigger if exists delete_tbl_aa_subject;
drop trigger if exists delete_tbl_aa_subject_login;

DELIMITER $$

create trigger insert_tbl_aa_subject after insert on tbl_aa_subject
  for each row begin
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'FIRST_NAME', null, NEW.FIRST_NAME);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'EMAIL', null, NEW.EMAIL);
  end;

$$

create trigger insert_tbl_aa_subject_login after insert on tbl_aa_subject_login
  for each row begin
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'LOGIN_NAME', null, NEW.LOGIN_NAME);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'PASSWORD', null, NEW.PASSWORD);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('CREATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.CREATED_BY, NEW.CREATED_DT, 'PASSWORD_CHANGED_DATE', null, NEW.PASSWORD_CHANGED_DATE);
  end;

$$

create trigger update_tbl_aa_subject after update on tbl_aa_subject
  for each row begin
    if (NEW.FIRST_NAME != OLD.FIRST_NAME) then
      insert into tbl_itrust_aud
        (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
      values
        ('UPDATE', 'tbl_aa_subject', NEW.SUBJECT_ID, NEW.UPDATED_BY, NEW.UPDATED_DT, 'FIRST_NAME', OLD.FIRST_NAME, NEW.FIRST_NAME);
    end if;
    if (NEW.EMAIL != OLD.EMAIL) then
      insert into tbl_itrust_aud
        (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
      values
        ('UPDATE', 'tbl_aa_subject', NEW.SUBJECT_ID, NEW.UPDATED_BY, NEW.UPDATED_DT, 'EMAIL', OLD.EMAIL, NEW.EMAIL);
    end if;
  end;

$$

create trigger update_tbl_aa_subject_login after update on tbl_aa_subject_login
  for each row begin
    if (NEW.LOGIN_NAME != OLD.LOGIN_NAME) then
      insert into tbl_itrust_aud
        (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
      values
        ('UPDATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.UPDATED_BY, NEW.UPDATED_DT, 'LOGIN_NAME', OLD.LOGIN_NAME, NEW.LOGIN_NAME);
    end if;
    if (NEW.PASSWORD != OLD.PASSWORD) then
      insert into tbl_itrust_aud
        (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
      values
        ('UPDATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.UPDATED_BY, NEW.UPDATED_DT, 'PASSWORD', OLD.PASSWORD, NEW.PASSWORD);
    end if;
    if (NEW.PASSWORD_CHANGED_DATE != OLD.PASSWORD_CHANGED_DATE) then
      insert into tbl_itrust_aud
        (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
      values
        ('UPDATE', 'tbl_aa_subject_login', NEW.SUBJECT_ID, NEW.UPDATED_BY, NEW.UPDATED_DT, 'PASSWORD_CHANGED_DATE', OLD.PASSWORD_CHANGED_DATE, NEW.PASSWORD_CHANGED_DATE);
    end if;
  end;

$$

create trigger delete_tbl_aa_subject before delete on tbl_aa_subject
  for each row begin
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('DELETE', 'tbl_aa_subject', OLD.SUBJECT_ID, OLD.UPDATED_BY, OLD.UPDATED_DT, 'FIRST_NAME', OLD.FIRST_NAME, null);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('DELETE', 'tbl_aa_subject', OLD.SUBJECT_ID, OLD.UPDATED_BY, OLD.UPDATED_DT, 'EMAIL', OLD.EMAIL, null);
    end;

$$

create trigger delete_tbl_aa_subject_login before delete on tbl_aa_subject_login
  for each row begin
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('DELETE', 'tbl_aa_subject_login', OLD.SUBJECT_ID, OLD.UPDATED_BY, OLD.UPDATED_DT, 'LOGIN_NAME', OLD.LOGIN_NAME, null);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('DELETE', 'tbl_aa_subject_login', OLD.SUBJECT_ID, OLD.UPDATED_BY, OLD.UPDATED_DT, 'PASSWORD', OLD.PASSWORD, null);
    insert into tbl_itrust_aud
      (txn_type, tbl_name, record_id, txn_author_id, txn_date, txn_field, txn_detail_bef, txn_detail_aft)
    values
      ('DELETE', 'tbl_aa_subject_login', OLD.SUBJECT_ID, OLD.UPDATED_BY, OLD.UPDATED_DT, 'PASSWORD_CHANGED_DATE', OLD.PASSWORD_CHANGED_DATE, null);
    end;

$$
