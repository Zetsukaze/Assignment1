DROP TABLE IF EXISTS TBL_CODE_INT;
DROP TABLE IF EXISTS TBL_CODETYPE;
DROP TABLE IF EXISTS tbl_menu_repository;
DROP TABLE IF EXISTS tbl_menu_item;
DROP TABLE IF EXISTS TBL_LOG_DELTA;
DROP TABLE IF EXISTS TBL_EMAIL_LOG;

DROP TABLE IF EXISTS TBL_CODE_INT_AUD;
DROP TABLE IF EXISTS TBL_CODETYPE_AUD;
DROP TABLE IF EXISTS TBL_AUDIT_REV_INFO_ASS;
DROP TABLE IF EXISTS TBL_AUDIT_REV_INFO;

CREATE TABLE TBL_CODETYPE (
  CODETYPE_ID varchar(20) NOT NULL,
  CODETYPE_DESC varchar(66) DEFAULT NULL,
  CODETYPE_TABLE varchar(66) DEFAULT NULL,
  READ_ONLY char(1) DEFAULT NULL,
  COL_CODETYPE_ID varchar(66) DEFAULT NULL,
  COL_CODE_ID varchar(66) DEFAULT NULL,
  COL_CODE_DESC varchar(66) DEFAULT NULL,
  COL_CODE_SEQ varchar(66) DEFAULT NULL,
  COL_STATUS varchar(66) DEFAULT NULL,
  COL_EFFECTIVE_DT varchar(66) DEFAULT NULL,
  COL_EXPIRY_DT varchar(66) DEFAULT NULL,
  EDIT_URL varchar(120) DEFAULT NULL,
  ADD_URL varchar(120) DEFAULT NULL,
  OWNER_GROUP varchar(200) DEFAULT NULL,
  UPDATED_BY varchar(32) DEFAULT NULL,
  UPDATED_DT datetime DEFAULT NULL,
  COL_CODE_LOCALE varchar(20) DEFAULT NULL,
  PRIMARY KEY (CODETYPE_ID)
);

CREATE TABLE TBL_CODE_INT (
  CODETYPE_ID varchar(20) NOT NULL,
  CODE_ID varchar(20) NOT NULL,
  PARENT_CODETYPE_ID varchar(20) DEFAULT NULL,
  PARENT_CODE_ID varchar(20) DEFAULT NULL,
  CODE_DESC varchar(66) DEFAULT NULL,
  CODE_SEQ INTEGER DEFAULT NULL,
  STATUS char(1) NOT NULL,
  EFFECTIVE_DT date DEFAULT NULL,
  EXPIRY_DT date DEFAULT NULL,
  UPDATED_BY varchar(32) DEFAULT NULL,
  UPDATED_DT datetime DEFAULT NULL,
  LOCALE varchar(2) NOT NULL,
  PRIMARY KEY (CODETYPE_ID,CODE_ID,LOCALE),
  FOREIGN KEY (CODETYPE_ID) REFERENCES TBL_CODETYPE(CODETYPE_ID)
);

CREATE TABLE TBL_MENU_ITEM (
  REPOSITORY_ID varchar(32) NOT NULL,
  ITEM_ID varchar(32) NOT NULL,
  PARENT_ITEM_ID varchar(32) DEFAULT NULL,
  ITEM_SEQ INTEGER DEFAULT NULL,
  NAME varchar(120) NOT NULL,
  TITLE varchar(120) DEFAULT NULL,
  LOCATION varchar(120) DEFAULT NULL,
  TARGET varchar(120) DEFAULT NULL,
  DESCRIPTION varchar(120) DEFAULT NULL,
  ON_CLICK varchar(120) DEFAULT NULL,
  ON_MOUSE_OVER varchar(120) DEFAULT NULL,
  ON_MOUSE_OUT varchar(120) DEFAULT NULL,
  IMAGE varchar(120) DEFAULT NULL,
  ALT_IMAGE varchar(120) DEFAULT NULL,
  TOOL_TIP varchar(120) DEFAULT NULL,
  ROLES varchar(120) DEFAULT NULL,
  PAGE varchar(120) DEFAULT NULL,
  CATEGORY varchar(120) DEFAULT NULL,
  VERSION INTEGER DEFAULT NULL,
  ICON_CSS varchar(255) DEFAULT NULL,
  USE_AJAX varchar(5) DEFAULT NULL,
  PRIMARY KEY (REPOSITORY_ID,NAME)
);

CREATE TABLE TBL_MENU_REPOSITORY (
  REPOSITORY_ID varchar(32) NOT NULL,
  REPOSITORY_DESC varchar(50) DEFAULT NULL,
  PRIMARY KEY (REPOSITORY_ID)
);

CREATE TABLE TBL_EMAIL_LOG (
  MAIL_LOG_ID VARCHAR(32) NOT NULL,
  BATCH_NAME  VARCHAR(32),
  EMAIL_FROM_NAME VARCHAR(100) NULL,
  EMAIL_FROM_ADDR VARCHAR(100) NULL,
  EMAIL_TO_NAME  VARCHAR(1000) NULL,
  EMAIL_TO_ADDR VARCHAR(1000) NULL,
  EMAIL_SUBJECT VARCHAR(255) NULL,
  EMAIL_CONTENT BLOB DEFAULT NULL,
  CREATED_BY VARCHAR(32) NOT NULL,
  CREATED_DT DATETIME NOT NULL,
  STATUS VARCHAR(10) NOT NULL,
  ERROR_MESSAGE BLOB DEFAULT NULL,
  VERSION INTEGER NOT NULL,
  PRIMARY KEY (MAIL_LOG_ID)
);


CREATE TABLE TBL_LOG_DELTA (
    DELTA_ID                       VARCHAR(32)      NOT NULL,
    TRANSACTION_ID                 VARCHAR(32)      NOT NULL,
    TRANSACTION_TYPE               VARCHAR(32)      NOT NULL,
    TABLE_NAME                     VARCHAR(32)      NOT NULL,
    PRIMARY_KEY                    VARCHAR(255)     NOT NULL,
    CREATED_BY                     VARCHAR(32)      NOT NULL,
    CREATED_DATE                   DATETIME         NOT NULL,
    TRANSACTION_DETAILS            BLOB             NOT NULL,
    LOG_ACTION                     VARCHAR(255)     NOT NULL
);

ALTER TABLE TBL_LOG_DELTA ADD CONSTRAINT
    PK_TBL_LOG_DELTA               PRIMARY KEY (DELTA_ID, TRANSACTION_ID);

-- For Audit Tables
CREATE TABLE TBL_AUDIT_REV_INFO (
  ID int(11) NOT NULL AUTO_INCREMENT,
  FUNC varchar(255) DEFAULT NULL,
  FUNC_NAME varchar(255) DEFAULT NULL,
  TIMESTAMP bigint(20) DEFAULT NULL,
  USER_ID varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE TBL_AUDIT_REV_INFO_ASS (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  BUSINESS_KEY varchar(255) DEFAULT NULL,
  ENTITY_ID blob,
  ENTITY_NAME varchar(255) DEFAULT NULL,
  TABLE_NAME varchar(255) DEFAULT NULL,
  REV_ID int(11) DEFAULT NULL,
  REV_TYPE varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (REV_ID) REFERENCES TBL_AUDIT_REV_INFO(ID)
);

CREATE TABLE TBL_CODE_INT_AUD (
  CODE_ID varchar(255) NOT NULL,
  CODETYPE_ID varchar(255) NOT NULL,
  LOCALE varchar(255) NOT NULL,
  REV int(11) NOT NULL,
  REV_TYPE tinyint(4) DEFAULT NULL,
  CODE_DESC varchar(255) DEFAULT NULL,
  CODE_SEQ int(11) DEFAULT NULL,
  EFFECTIVE_DT datetime DEFAULT NULL,
  EXPIRY_DT datetime DEFAULT NULL,
  PARENT_CODE_ID varchar(255) DEFAULT NULL,
  PARENT_CODETYPE_ID varchar(255) DEFAULT NULL,
  STATUS varchar(255) DEFAULT NULL,
  UPDATED_BY varchar(255) DEFAULT NULL,
  UPDATED_DT datetime DEFAULT NULL,
  PRIMARY KEY (CODE_ID,CODETYPE_ID,LOCALE,REV), 
  FOREIGN KEY (REV) REFERENCES TBL_AUDIT_REV_INFO(ID)
);

CREATE TABLE TBL_CODETYPE_AUD (
  CODETYPE_ID varchar(255) NOT NULL,
  REV int(11) NOT NULL,
  REV_TYPE tinyint(4) DEFAULT NULL,
  ADD_URL varchar(255) DEFAULT NULL,
  CODETYPE_DESC varchar(255) DEFAULT NULL,
  CODETYPE_TABLE varchar(255) DEFAULT NULL,
  COL_CODE_DESC varchar(255) DEFAULT NULL,
  COL_CODE_ID varchar(255) DEFAULT NULL,
  COL_CODE_SEQ varchar(255) DEFAULT NULL,
  COL_CODETYPE_ID varchar(255) DEFAULT NULL,
  COL_EFFECTIVE_DT varchar(255) DEFAULT NULL,
  COL_EXPIRY_DT varchar(255) DEFAULT NULL,
  COL_STATUS varchar(255) DEFAULT NULL,
  EDIT_URL varchar(255) DEFAULT NULL,
  COL_CODE_LOCALE varchar(255) DEFAULT NULL,
  OWNER_GROUP varchar(255) DEFAULT NULL,
  READ_ONLY varchar(255) DEFAULT NULL,
  UPDATED_BY varchar(255) DEFAULT NULL,
  UPDATED_DT datetime DEFAULT NULL,
  PRIMARY KEY (CODETYPE_ID,REV),  
  FOREIGN KEY (REV) REFERENCES TBL_AUDIT_REV_INFO(ID)
);

insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('acm_res','ACM Resources Type','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('addr_type','Address Type','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('application','Application List','TBL_AA_APP','Y',null,'APP_ID','APP_NAME','APP_NAME',null,null,null,null,null,null,null,null,null);
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('auditLevel','Log Audit Level','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('boolean_yn','Yes / No','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('contact','Types Of Contact','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('country','Country','TBL_CODE_COUNTRY','N',null,'COUNTRY_ID','COUNTRY_DESC',null,'STATUS',null,null,'/codeadmin/viewedit_country.do','/codeadmin/add_country.do','DEF-group-groupA',null,null,'COUNTRY_LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('dateType','time date Type','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('gender','Gender','TBL_CODE_INT','N','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do','/codeadmin/add_int.do?codeInt.codeTypeId=gender&codeInt.codeTypeDesc=Gender','DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('industry','Industry','TBL_SAMPLE_INDUSTRY','Y',null,'INDUSTRY_ID','INDUSTRY_DESC',null,'STATUS',null,null,'/codeadmin/viewedit_industry.do','/codeadmin/add_industry.do','DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('loginType','loginType','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('martial','Martial Status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('process_st','Process Status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,null,null,null,null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('pubDest','publisher Dest','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,null,null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('pubStatus','publisher Status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,null,null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('rec_type','Record Type','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('salutation','Salutation','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('schStatus','Schedule status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,null,null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('simple_status','simple status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('status','Status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('task_st','Task Status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,null,null,null,null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('translogtype','Transaction Type','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/codeadmin/viewedit_int.do',null,null,null,null,'LOCALE');
insert into TBL_CODETYPE (CODETYPE_ID, CODETYPE_DESC, CODETYPE_TABLE, READ_ONLY, COL_CODETYPE_ID, COL_CODE_ID, COL_CODE_DESC, COL_CODE_SEQ, COL_STATUS, COL_EFFECTIVE_DT, COL_EXPIRY_DT, EDIT_URL, ADD_URL, OWNER_GROUP, UPDATED_BY, UPDATED_DT, COL_CODE_LOCALE) values('wregion','World Region','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ',null,null,null,'/codeadmin/viewedit_int.do',null,'DEF-group-groupA',null,null,'LOCALE');


insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('acm_res','URI','URI',1,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('acm_res','ACTION','JSF Action',2,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('acm_res','REPORT_CATEGORY','Report Category',3,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('acm_res','WEBSERVICE','WebService',4,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('acm_res','RESTSERVICE','RESTful Service',5,'A',null,null,null,null,'en');

insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','CONTAINER','Container Login',6,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','KERBEROS','Kerberos Login',5,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','LDAP','LDAP Login',7,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','NETRUST','Netrust Login',4,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','PASSWORD','Password Login',1,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','SINGPASS','Singpass Login',2,'A','2011-03-24','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('loginType','SINGPASS_EASY','Singpass With Easy Login',3,'A','2011-02-11','2008-01-01',null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('process_st','A','Active',1,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('process_st','C','Completed',2,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('process_st','F','Failed',6,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('process_st','K','Creating',5,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('process_st','S','Suspended',3,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('process_st','T','Terminated',4,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('pubDest','File','File',2,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('pubDest','FTP','FTP',1,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('pubStatus','A','Active',1,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('pubStatus','I','Inactive',2,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('schStatus','C','Successful',6,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('schStatus','F','Failed',4,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('schStatus','N','Unscheduled',5,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('schStatus','P','Published',2,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('schStatus','S','Scheduled',1,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('schStatus','U','Unpublished',3,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('task_st','C','Complete Failed',3,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('task_st','P','Processing',2,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('task_st','S','Skip Failed',3,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('task_st','W','Waiting',1,'A',null,null,'DEF-user-useradmin',null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('translogtype','DELETE','DELETE',3,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('translogtype','INSERT','INSERT',1,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values('translogtype','UPDATE','UPDATE',2,'A',null,null,null,null,'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values ('status','A','Active',1,'A',null,null,null,now(),'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values ('status','I','Inactive',3,'A',null,null,null,now(),'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values ('status','P','Pending',5,'P',null,null,null,now(),'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values ('status','R','Revoked',4,'A',null,null,null,now(),'en');
insert into TBL_CODE_INT (CODETYPE_ID, CODE_ID, CODE_DESC, CODE_SEQ, STATUS, EFFECTIVE_DT, EXPIRY_DT, UPDATED_BY, UPDATED_DT, LOCALE) values ('status','S','Suspended',2,'A',null,null,null,now(),'en');