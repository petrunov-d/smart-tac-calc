create table user_access
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    username  varchar(50)           not null,
    user_access varchar(250)           not null,
    constraint fk_user_access_users foreign key (username) references users (username)
);