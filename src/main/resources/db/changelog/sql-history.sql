--liquibase formatted sql

--changeset tomasz:1545348072653-1
CREATE SEQUENCE hibernate_sequence;

--changeset tomasz:1545348072653-2
CREATE TABLE book (id UUID NOT NULL, isbn VARCHAR(255), author VARCHAR(255), book_search VARCHAR(255), book_state VARCHAR(255), date date, description VARCHAR(4096), publisher VARCHAR(255), quant INT NOT NULL, title VARCHAR(255), book_category_id BIGINT, library_id UUID, user_casual_id BIGINT, user_menager_id BIGINT);

--changeset tomasz:1545348072653-3
CREATE TABLE book_category (id BIGSERIAL NOT NULL, category_type VARCHAR(255), CONSTRAINT book_category_pkey PRIMARY KEY (id));

--changeset tomasz:1545348072653-4
CREATE TABLE library (id UUID NOT NULL, city VARCHAR(255), email VARCHAR(255), latitude VARCHAR(255), local VARCHAR(255), longitude VARCHAR(255), name VARCHAR(255), number VARCHAR(255), postal_code VARCHAR(255), street VARCHAR(255), user_menager_id BIGINT);

--changeset tomasz:1545348072653-5
CREATE TABLE roles (id BIGSERIAL NOT NULL, user_role VARCHAR(255), user_id UUID, CONSTRAINT roles_pkey PRIMARY KEY (id));

--changeset tomasz:1545348072653-6
CREATE TABLE spring_session (primary_id CHAR(36) NOT NULL, session_id CHAR(36) NOT NULL, creation_time BIGINT NOT NULL, last_access_time BIGINT NOT NULL, max_inactive_interval INT NOT NULL, expiry_time BIGINT NOT NULL, principal_name VARCHAR(100));

--changeset tomasz:1545348072653-7
CREATE TABLE spring_session_attributes (session_primary_id CHAR(36) NOT NULL, attribute_name VARCHAR(200) NOT NULL, attribute_bytes BYTEA NOT NULL);

--changeset tomasz:1545348072653-8
CREATE TABLE "user" (id UUID NOT NULL, email VARCHAR(255), firstname VARCHAR(255), lastname VARCHAR(255), password VARCHAR(255), phone_number INT NOT NULL, user_casual_id BIGINT, user_menager_id BIGINT);

--changeset tomasz:1545348072653-9
CREATE TABLE user_casual (id BIGINT NOT NULL);

--changeset tomasz:1545348072653-10
CREATE TABLE user_menager (id BIGINT NOT NULL);

--changeset tomasz:1545348072653-11
ALTER TABLE book ADD CONSTRAINT book_pkey PRIMARY KEY (id);

--changeset tomasz:1545348072653-12
ALTER TABLE library ADD CONSTRAINT library_pkey PRIMARY KEY (id);

--changeset tomasz:1545348072653-13
ALTER TABLE spring_session_attributes ADD CONSTRAINT spring_session_attributes_pk PRIMARY KEY (session_primary_id, attribute_name);

--changeset tomasz:1545348072653-14
ALTER TABLE spring_session ADD CONSTRAINT spring_session_pk PRIMARY KEY (primary_id);

--changeset tomasz:1545348072653-15
ALTER TABLE user_casual ADD CONSTRAINT user_casual_pkey PRIMARY KEY (id);

--changeset tomasz:1545348072653-16
ALTER TABLE user_menager ADD CONSTRAINT user_menager_pkey PRIMARY KEY (id);

--changeset tomasz:1545348072653-17
ALTER TABLE "user" ADD CONSTRAINT user_pkey PRIMARY KEY (id);

--changeset tomasz:1545348072653-18
CREATE UNIQUE INDEX spring_session_ix1 ON spring_session(session_id);

--changeset tomasz:1545348072653-19
CREATE INDEX spring_session_ix2 ON spring_session(expiry_time);

--changeset tomasz:1545348072653-20
CREATE INDEX spring_session_ix3 ON spring_session(principal_name);

--changeset tomasz:1545348072653-21
ALTER TABLE "user" ADD CONSTRAINT fk4595vgwgto4w9kg8r8uaoklwj FOREIGN KEY (user_menager_id) REFERENCES user_menager (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-22
ALTER TABLE roles ADD CONSTRAINT fk9b9ghr3c00v40wy9mx3l5877w FOREIGN KEY (user_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-23
ALTER TABLE "user" ADD CONSTRAINT fk9m4j1tsh0g6nihw7htn4cfy2w FOREIGN KEY (user_casual_id) REFERENCES user_casual (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-24
ALTER TABLE book ADD CONSTRAINT fkaojxagnfmppd09k35kye5eph5 FOREIGN KEY (library_id) REFERENCES library (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-25
ALTER TABLE book ADD CONSTRAINT fkefmmh1v9x7a0vw4gpa3d63gr1 FOREIGN KEY (user_casual_id) REFERENCES user_casual (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-26
ALTER TABLE library ADD CONSTRAINT fkfoxuqlyqetnbk7uefpah6jxl FOREIGN KEY (user_menager_id) REFERENCES user_menager (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-27
ALTER TABLE book ADD CONSTRAINT fks8rqq96mvfrfsj9euw5mn973t FOREIGN KEY (book_category_id) REFERENCES book_category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-28
ALTER TABLE book ADD CONSTRAINT fks9nf4ua7lav8qftiqcdfuhmnw FOREIGN KEY (user_menager_id) REFERENCES user_menager (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset tomasz:1545348072653-29
ALTER TABLE spring_session_attributes ADD CONSTRAINT spring_session_attributes_fk FOREIGN KEY (session_primary_id) REFERENCES spring_session (primary_id) ON UPDATE NO ACTION ON DELETE CASCADE;

