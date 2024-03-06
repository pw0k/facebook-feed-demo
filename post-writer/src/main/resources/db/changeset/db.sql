--liquibase formatted sql

--changeset timeline:1
CREATE TABLE fb_user
(
    id       SERIAL primary key,
    username TEXT NOT NULL UNIQUE,
    email    TEXT NOT NULL UNIQUE
);

--changeset timeline:2
CREATE TABLE fb_group
(
    id          SERIAL primary key,
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);

--changeset timeline:3
CREATE TABLE post
(
    id          BIGSERIAL primary key,
    title       TEXT NOT NULL,
    description TEXT NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE,
    user_id     INT  NOT NULL REFERENCES fb_user (id),
    group_id    INT REFERENCES fb_group (id)
);

--changeset timeline:4
CREATE TABLE user_follow
(
    user_id        INT NOT NULL REFERENCES fb_user (id),
    follow_user_id INT NOT NULL REFERENCES fb_user (id),
    PRIMARY KEY (user_id, follow_user_id)
);

--changeset timeline:5
CREATE TABLE user_group
(
    user_id  INT NOT NULL REFERENCES fb_user (id),
    group_id INT NOT NULL REFERENCES fb_group (id)
);

--changeset timeline:6
CREATE TABLE user_post
(
    post_id BIGINT NOT NULL REFERENCES post (id),
    user_id INT    NOT NULL REFERENCES fb_user (id)
);

--changeset timeline:7
CREATE TABLE group_post
(
    post_id  BIGINT NOT NULL REFERENCES post (id),
    group_id INT    NOT NULL REFERENCES fb_group (id)
);

--changeset timeline:8
CREATE TABLE post_outbox
(
    id                     BIGSERIAL PRIMARY KEY,
    post_id                BIGINT NOT NULL REFERENCES post (id),
    title                  TEXT        NOT NULL,
    description            TEXT        NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE,
    username               TEXT        NOT NULL,
    groupname              TEXT,
    status                 VARCHAR(50) NOT NULL,
    post_outbox_event_type VARCHAR(50) NOT NULL
);