-- REGIONS
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

-- ACCOUNTS
DROP TABLE IF EXISTS accounts CASCADE;
DROP SEQUENCE IF EXISTS accounts_id_seq;

CREATE SEQUENCE accounts_id_seq INCREMENT 1 START 1;
CREATE TABLE accounts
(
    id         integer      NOT NULL DEFAULT nextval('accounts_id_seq'::regclass),
    username   varchar(100) NOT NULL,
    password   varchar(255) NOT NULL,
    created_at timestamp    NOT NULL,
    role       varchar(20)  NOT NULL,
    name       varchar(255) NOT NULL,
    CONSTRAINT PK_ACCOUNTS_ID PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IX_ACCOUNTS_USERNAME_UNIQ ON accounts (username);
