SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS conversion DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE conversion ;

drop table if exists conversion.word_end;

create table if not exists conversion.word_end
(
    id       int auto_increment
        primary key,
    name     varchar(20) not null,
    language varchar(80)  not null,
    end_blob longblob     null
);

drop table if exists conversion.user_details;

create table if not exists conversion.user_details
(
    id         int auto_increment
        primary key,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    email      varchar(255) null,
    phone      varchar(255) null
);

drop table if exists conversion.profile;

create table if not exists conversion.profile
(
    id             int auto_increment
        primary key,
    profile_name   varchar(120) not null,
    developer_name varchar(120) not null
);

drop table if exists conversion.user1;

create table if not exists conversion.user1
(
    id              int auto_increment,
    profile_id      int         not null,
    password1       varchar(80) not null,
    login           varchar(80) null,
    user_details_id int         null,
    constraint user1_id_uindex
        unique (id),
    constraint user1_login_uindex
        unique (login),
    constraint user1_profile_id_fk
        foreign key (profile_id) references profile (id),
    constraint user1_userdetails_id_fk
        foreign key (user_details_id) references user_details (id)
);

alter table user1
    add primary key (id);

drop table if exists conversion.conversion_record;

create table if not exists conversion.conversion_record
(
    id                          int auto_increment
        primary key,
    file_name                   varchar(255)         not null,
    conversion_source_type      enum ('txt')         null,
    conversion_destination_type enum ('mp3')         null,
    created_date                date                 null,
    is_error                    tinyint(1) default 0 null,
    is_converted                tinyint(1) default 0 null,
    created_by                  int                  null,
    source_file_blob            longblob             null,
    destination_file_blob       longblob             null,
    constraint conversion_user1_id_fk
        foreign key (created_by) references user1 (id)
);

drop table if exists conversion.audio_word;

create table if not exists conversion.audio_word
(
    id              int auto_increment
        primary key,
    word_string     varchar(255)               not null,
    language        varchar(255)               null,
    extension       enum ('.mp3', 'undefined') not null,
    created_by      int                        null,
    audio_word_blob longblob                   null,
    is_standard     tinyint(1)                 null,
    constraint audio_word_user1_id_fk
        foreign key (created_by) references user1 (id)
);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


insert into profile (profile_name, developer_name) value ('Administrator', 'Administrator');
insert into profile (profile_name, developer_name) value ('Standard User', 'Standard_User');

insert into user_details (first_name, last_name, email, phone)
    values ('AdministratorFirstName', 'AdministratorLastName', 'email@test.com', '01249345123');

insert into user1 (login, password1, profile_id, user_details_id)
	values ('admin', 'admin',
	        (select id from profile where developer_name = 'Administrator'),
	        (select id from user_details where email = 'email@test.com'));

insert into user_details (first_name, last_name, email, phone)
values ('StandardUserFirstName', 'StandardUserLastName', 'email2@test.com', '01249345123');

insert into user1 (login, password1, profile_id, user_details_id)
values ('user', '1111',
        (select id from profile where developer_name = 'Standard_User'),
        (select id from user_details where email = 'email2@test.com'));

