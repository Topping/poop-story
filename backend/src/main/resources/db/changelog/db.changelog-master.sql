-- liquibase formatted sql

-- changeset nik:2023-04-07
CREATE SCHEMA poopstory;

CREATE TABLE poopstory.pooper(
    id uuid NOT NULL,
    version BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    pooper_ext_ref text NOT NULL,
    CONSTRAINT pooper_pk PRIMARY KEY (id),
    CONSTRAINT pooper_unique UNIQUE (pooper_ext_ref)
);
CREATE  UNIQUE  INDEX  idx_ext_ref ON poopstory.pooper(pooper_ext_ref DESC);

CREATE TABLE poopstory.event(
    id uuid NOT NULL,
    version BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    pooper_id uuid REFERENCES poopstory.pooper (id),
    location geometry NOT NULL,
    CONSTRAINT event_pk PRIMARY KEY (id),
    CONSTRAINT pooper_fk FOREIGN KEY (pooper_id) REFERENCES poopstory.pooper (id)
    ON DELETE CASCADE
);
CREATE INDEX poop_event_geom_idx ON poopstory.event USING gist (location gist_geometry_ops_nd);

CREATE TABLE poopstory.users(
    id uuid NOT NULL,
    version BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    subject text NOT NULL,
    email text NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT subject_unique UNIQUE (subject),
    CONSTRAINT email_unique UNIQUE (email)
);

CREATE TABLE poopstory.groups(
    id uuid NOT NULL,
    version BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    creator_id uuid NOT NULL,
    name text NOT NULL,
    visibility text NOT NULL,
    CONSTRAINT group_pk PRIMARY KEY (id),
    CONSTRAINT creator_fk FOREIGN KEY (creator_id)
        REFERENCES poopstory.users (id) MATCH FULL
        ON DELETE CASCADE
);

CREATE TABLE poopstory.group_members
(
    group_id uuid NOT NULL,
    user_id uuid NOT NULL,
    role text NOT NULL,
    CONSTRAINT group_member_pk PRIMARY KEY (group_id, user_id),
    CONSTRAINT group_fk FOREIGN KEY (group_id)
        REFERENCES poopstory.groups (id) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES poopstory.users (id) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

-- changeset nik:2023-04-08:1
CREATE TABLE poopstory.happenings(
    id uuid NOT NULL,
    happening_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    creator_id uuid NOT NULL,
    location geometry NOT NULL,
    type text NOT NULL,
    CONSTRAINT happening_pk PRIMARY KEY (id),
    CONSTRAINT creator_fk FOREIGN KEY (creator_id)
        REFERENCES poopstory.users (id) MATCH FULL
        ON DELETE CASCADE
);