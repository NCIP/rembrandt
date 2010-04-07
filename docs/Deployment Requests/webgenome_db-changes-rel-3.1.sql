ALTER TABLE principal ADD (first_name VARCHAR2(40) NULL);
ALTER TABLE principal ADD (last_name VARCHAR2(40) NULL);
ALTER TABLE principal ADD (email VARCHAR2(40) NULL);
ALTER TABLE principal ADD (institution VARCHAR2(255) NULL);
ALTER TABLE principal ADD (department VARCHAR2(255) NULL);
ALTER TABLE principal ADD (position VARCHAR2(255) NULL);
ALTER TABLE principal ADD (degree VARCHAR2(40) NULL);
ALTER TABLE principal ADD (phone VARCHAR2(40) NULL);
ALTER TABLE principal ADD (address VARCHAR2(400) NULL);
ALTER TABLE principal ADD (is_feedbacks CHAR(1) NULL);


ALTER TABLE job ADD(params varchar(255) NULL);
ALTER TABLE job ADD (bioassay_id NUMBER NULL);