-- liquibase formatted sql

-- changeset nik:2023-04-07
CREATE SCHEMA poopstory;

-- changeset nik:2023-04-27:1
CREATE TABLE poopstory.visits(
    id uuid NOT NULL,
    country_id uuid NOT NULL,
    visit_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    creator_id text NOT NULL,
    location geometry NOT NULL,
    type text NOT NULL,
    rating real NOT NULL,
    CONSTRAINT visit_pk PRIMARY KEY (id)
);
CREATE INDEX idx_visit_creator_id ON poopstory.visits USING btree(creator_id);
CREATE INDEX idx_visit_location ON poopstory.visits USING gist (location);
CREATE INDEX idx_visit_country ON poopstory.visits USING btree(country_id);

-- changeset nik:2023-04-29:1
CREATE TABLE poopstory.countries(
    id uuid NOT NULL,
    common_name text NOT NULL,
    iso3_code char(3) NOT NULL,
    iso2_code char(2) NOT NULL,
    boundary geography NOT NULL,
    CONSTRAINT country_pk PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (common_name)
);

-- changeset nik:2023-04-29:2
CREATE INDEX idx_country_geography ON poopstory.countries USING gist(boundary);
CREATE INDEX idx_country_common_name ON poopstory.countries USING btree(common_name);
CREATE INDEX idx_country_iso3_code ON poopstory.countries USING btree(iso3_code);
CREATE INDEX idx_country_iso2_code ON poopstory.countries USING btree(iso2_code);