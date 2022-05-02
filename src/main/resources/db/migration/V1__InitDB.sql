create table users
(
    id bigint auto_increment primary key,
    created timestamp default current_timestamp,
    updated timestamp on update current_timestamp,
    username varchar(256) not null,
    password varchar(256) not null,
    email varchar(256),
    active boolean default false,
    role varchar(32),
    last_name varchar(64),
    first_name varchar(64),
    middle_name varchar(64),
    activation_code varchar(256),
    index idx_users_fio(last_name, first_name, middle_name),
    constraint unq_users_username unique(username),
    constraint unq_users_email unique(email)
);
