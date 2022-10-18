--
-- PostgreSQL database dump
--
CREATE USER r2dbc with password 'r2dbc';
CREATE USER jpa with password 'jpa';
GRANT ALL PRIVILEGES ON DATABASE postgres to r2dbc;
GRANT ALL PRIVILEGES ON DATABASE postgres to jpa;

-- Dumped from database version 9.2.0
-- Dumped by pg_dump version 9.2.0
-- Started on 2013-05-19 16:05:10 BST

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 32769)
-- Name: cd; Type: SCHEMA; Schema: -; Owner: -
--

SET default_tablespace = '';
SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 32818)
-- Name: bookings; Type: TABLE; Schema: cd; Owner: -; Tablespace:
--

--
-- TOC entry 169 (class 1259 OID 32770)
-- Name: facilities; Type: TABLE; Schema: cd; Owner: -; Tablespace:
--

CREATE TABLE facilities (
    facid SERIAL NOT NULL PRIMARY KEY,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);


--
-- TOC entry 170 (class 1259 OID 32800)
-- Name: members; Type: TABLE; Schema: cd; Owner: -; Tablespace:
--

CREATE TABLE members (
    memid SERIAL NOT NULL PRIMARY KEY,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL,
    FOREIGN KEY (recommendedby) REFERENCES members(memid) ON DELETE SET NULL
);

CREATE TABLE bookings (
    bookid SERIAL NOT NULL PRIMARY KEY,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL,
        FOREIGN KEY (facid) REFERENCES facilities(facid),
        FOREIGN KEY (memid) REFERENCES members(memid)
);



CREATE INDEX "bookings.memid_facid"
  ON bookings
  USING btree
  (memid, facid);

CREATE INDEX "bookings.facid_memid"
  ON bookings
  USING btree
  (facid, memid);

CREATE INDEX "bookings.facid_starttime"
  ON bookings
  USING btree
  (facid, starttime);

CREATE INDEX "bookings.memid_starttime"
  ON bookings
  USING btree
  (memid, starttime);

CREATE INDEX "bookings.starttime"
  ON bookings
  USING btree
  (starttime);

CREATE INDEX "members.joindate"
  ON members
  USING btree
  (joindate);

CREATE INDEX "members.recommendedby"
  ON members
  USING btree
  (recommendedby);


GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO r2dbc;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO jpa;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO r2dbc;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO jpa;

CREATE OR REPLACE FUNCTION notify_member_saved()
    RETURNS TRIGGER
AS $$
BEGIN
    PERFORM pg_notify('MEMBER_SAVED',  row_to_json(NEW)::text);
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER member_saved_trigger
    AFTER INSERT OR UPDATE
    ON members
    FOR EACH ROW
EXECUTE PROCEDURE notify_member_saved();

CREATE OR REPLACE FUNCTION notify_member_deleted()
    RETURNS TRIGGER
AS $$
BEGIN
    PERFORM pg_notify('MEMBER_DELETED',  row_to_json(NEW)::text);
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER member_deleted_trigger
    AFTER DELETE
    ON members
    FOR EACH ROW
EXECUTE PROCEDURE notify_member_deleted();