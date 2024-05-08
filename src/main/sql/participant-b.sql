/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  NATCRI
 * Created: 4 dic 2023
 */
CREATE SCHEMA IF NOT EXISTS participant_b;

CREATE SEQUENCE participant_b.entity_b_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS participant_b.entity_b
(
    id bigint NOT NULL DEFAULT nextval('participant_b.entity_b_id_seq'::regclass),
    optlock bigint NOT NULL DEFAULT 1,
    event_uuid character varying(36),
    event_state smallint,
    entity_a bigint NOT NULL,
    title character varying(255) NOT NULL,
    enabled boolean NOT NULL DEFAULT true,
    weight int NOT NULL DEFAULT 0,
    createtime timestamp with time zone NOT NULL,
    dbtime timestamp with time zone NOT NULL DEFAULT now()
);

-- Index: entity_b_id_idx
-- DROP INDEX participant_b.entity_b_id_idx;
CREATE INDEX entity_b_id_idx ON participant_b.entity_b USING btree(id);

-- Index: entity_b_event_uuid_idx
-- DROP INDEX participant_b.entity_b_event_uuid_idx;
CREATE INDEX entity_b_event_uuid_idx ON participant_b.entity_b USING btree(event_uuid);

-- Index: entity_b_event_state_idx
-- DROP INDEX participant_b.entity_b_event_state_idx;
CREATE INDEX entity_b_event_state_idx ON participant_b.entity_b USING btree(event_state);
