create table users
(
    username varchar(500) PRIMARY KEY NOT NULL,
    password varchar(500)             not null,
    enabled  boolean                  not null
);

create table authorities
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    username  varchar(50)           not null,
    authority varchar(50)           not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index ix_auth_username on authorities (username, authority);

-- 87E8DyX4pg835NR
INSERT INTO public.users (username, "password", enabled)
VALUES ('admin', '$2y$12$KcdLOzekJy5nC01jBEpuA.gyTeuPd3PawPOve81DAEneBOuXaYViO', true);

INSERT INTO public.authorities(username, authority)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO public.authorities(username, authority)
VALUES ('admin', 'ROLE_USER');

-- 47G6qGj3354dgwP
INSERT INTO public.users (username, "password", enabled)
VALUES ('default_user', '$2y$12$lzcL/5nJsDqQE5PMJixVQ.bFl89sFDyxhCenIvBcd.rvh6bBu4eBq', true);

INSERT INTO public.authorities(username, authority)
VALUES ('default_user', 'ROLE_USER');