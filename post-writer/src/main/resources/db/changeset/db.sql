--liquibase formatted sql

--changeset facebook-feed-demo:1
CREATE TABLE fb_user
(
    id       SERIAL primary key,
    username TEXT NOT NULL UNIQUE,
    email    TEXT NOT NULL UNIQUE
);

--changeset facebook-feed-demo:2
CREATE TABLE fb_group
(
    id          SERIAL primary key,
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);

--changeset facebook-feed-demo:3
CREATE SEQUENCE post_id_seq
    START WITH 1
    INCREMENT BY 10 CACHE 10;

--changeset facebook-feed-demo:4
CREATE TABLE post
(
    id          BIGINT NOT NULL DEFAULT nextval('post_id_seq') PRIMARY KEY,
    title       TEXT   NOT NULL,
    description TEXT   NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE,
    user_id     INT    NOT NULL REFERENCES fb_user (id),
    group_id    INT REFERENCES fb_group (id)
);

--changeset facebook-feed-demo:5
CREATE TABLE user_follow
(
    user_id        INT NOT NULL REFERENCES fb_user (id),
    follow_user_id INT NOT NULL REFERENCES fb_user (id),
    PRIMARY KEY (user_id, follow_user_id)
);

--changeset facebook-feed-demo:6
CREATE TABLE user_group
(
    user_id  INT NOT NULL REFERENCES fb_user (id),
    group_id INT NOT NULL REFERENCES fb_group (id)
);

--changeset facebook-feed-demo:7
CREATE TABLE user_post
(
    post_id BIGINT NOT NULL REFERENCES post (id),
    user_id INT    NOT NULL REFERENCES fb_user (id)
);

--changeset facebook-feed-demo:8
CREATE TABLE group_post
(
    post_id  BIGINT NOT NULL REFERENCES post (id),
    group_id INT    NOT NULL REFERENCES fb_group (id)
);

--changeset facebook-feed-demo:9
CREATE SEQUENCE post_outbox_id_seq
    START WITH 1
    INCREMENT BY 10 CACHE 10;

--changeset facebook-feed-demo:10
CREATE TABLE post_outbox
(
    id                     BIGINT      NOT NULL     DEFAULT nextval('post_outbox_id_seq') PRIMARY KEY,
    post_id                BIGINT      NOT NULL REFERENCES post (id),
    title                  TEXT        NOT NULL,
    description            TEXT        NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE,
    updated_at             TIMESTAMP WITH TIME ZONE default current_timestamp,
    username               TEXT        NOT NULL,
    groupname              TEXT,
    post_outbox_status     VARCHAR(50) NOT NULL,
    post_outbox_event_type VARCHAR(50) NOT NULL
);
-- post_id                BIGINT      NOT NULL REFERENCES post (id),
