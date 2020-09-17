-- ZONES
DROP TABLE IF EXISTS regions CASCADE;
DROP SEQUENCE IF EXISTS regions_id_seq;

CREATE SEQUENCE regions_id_seq INCREMENT 1 START 1;
CREATE TABLE regions
(
    id      integer      NOT NULL DEFAULT nextval('regions_id_seq'::regclass),
    name    varchar(255) NOT NULL,
    capital varchar(255) NOT NULL,
    area    numeric(10, 2),
    CONSTRAINT PK_REGION_ID PRIMARY KEY (id)
);
