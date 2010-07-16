
-- Patients with one limited access are swithed to public.
-- AJ July 01, 2010

SELECT COUNT(*) FROM ARRAY_GENO_ABN_FACT
WHERE INSTITUTION_ID IN (5, 11);

DROP INDEX ABNF_INSTID_BIDX;

DROP INDEX ABNF_INS_SNPID_IDX;

DROP INDEX ABNF_SPECINST_IDX;

DROP INDEX ABNF_SPECSNPIDINST_IDX;


UPDATE ARRAY_GENO_ABN_FACT SET INSTITUTION_ID = 8
WHERE INSTITUTION_ID IN (5, 11);

COMMIT;


CREATE BITMAP INDEX ABNF_INSTID_BIDX ON ARRAY_GENO_ABN_FACT
(INSTITUTION_ID);


CREATE INDEX ABNF_INS_SNPID_IDX ON ARRAY_GENO_ABN_FACT
(INSTITUTION_ID, SNP_PROBESET_ID);


CREATE INDEX ABNF_SPECINST_IDX ON ARRAY_GENO_ABN_FACT
(SPECIMEN_NAME, INSTITUTION_ID);


CREATE INDEX ABNF_SPECSNPIDINST_IDX ON ARRAY_GENO_ABN_FACT
(SPECIMEN_NAME, INSTITUTION_ID, SNP_PROBESET_ID);



DROP TABLE SPECIMEN_GE;

create table SPECIMEN_GE as
select unique INSTITUTION_ID, SPECIMEN_NAME
from DIFFERENTIAL_EXPRESSION_SFACT
where institution_id <> -1;

DROP TABLE SPECIMEN_SNP;

create table SPECIMEN_SNP as
select unique INSTITUTION_ID, SPECIMEN_NAME
from ARRAY_GENO_ABN_FACT;



CREATE OR REPLACE VIEW REMB_STAT_V
(INSTITUTION_ID, SPECI_CLIN, PT_CLIN, SPECI_GE, PT_GE, 
 SPECI_CP, PT_CP)
AS 
select institution_id, max(SPECI_CLIN) SPECI_CLIN, max(PT_CLIN) PT_CLIN, max(SPECI_GE) SPECI_GE, max(PT_GE) PT_GE, max(SPECI_CP) SPECI_CP, max(PT_CP) PT_CP 
from ( 
select institution_id, count(unique SPECIMEN_NAME) speci_clin, null pt_clin, null speci_ge, null pt_ge, null speci_cp, null pt_cp 
from PATIENT_DATA 
group by institution_id 
union 
select institution_id, null, count(unique PATIENT_DID) pt_clin, null, null, null, null 
from PATIENT_DATA 
group by institution_id 
union 
select institution_id, null, null, count(unique SPECIMEN_NAME) speci_ge, null, null, null 
from SPECIMEN_GE 
group by institution_id 
union 
select a.institution_id, null, null, null, count(UNIQUE B.sample_ID) pt_ge, null, null 
from SPECIMEN_GE A, PATIENT_DATA B 
where A.SPECIMEN_NAME = B.SPECIMEN_NAME 
group by a.institution_id 
union 
select institution_id, null, null, null, null, count(UNIQUE SPECIMEN_NAME) speci_cp, null 
from SPECIMEN_SNP 
group by institution_id 
union 
select a.institution_id, null, null, null, null, null, count(UNIQUE B.sample_ID) pt_cp 
from SPECIMEN_SNP A, PATIENT_DATA B 
where A.SPECIMEN_NAME = B.SPECIMEN_NAME 
group by a.institution_id) 
group by institution_id;

