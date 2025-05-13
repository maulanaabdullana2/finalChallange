
CREATE SEQUENCE person.users_user_id_seq INCREMENT 1;
CREATE TABLE IF NOT EXISTS person.users
(
    user_id integer NOT NULL DEFAULT nextval('person.users_user_id_seq'::regclass),
    user_name character varying(15),
    user_email character varying(80),
    user_password character varying(125),
    user_handphone character varying(25),
    created_on timestamp without time zone,
    location_id integer,
    CONSTRAINT pk_user_id PRIMARY KEY (user_id),
    CONSTRAINT uq_user_email UNIQUE (user_email),
    CONSTRAINT uq_user_handphone UNIQUE (user_handphone),
    CONSTRAINT fk_user_locations FOREIGN KEY (location_id)
        REFERENCES hr.locations (location_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)