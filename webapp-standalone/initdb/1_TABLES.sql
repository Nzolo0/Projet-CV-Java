create table users
(
    id bigint auto_increment,
    constraint users_pk
        primary key (id),
    first_name TEXT not null,
    last_name TEXT not null,
    age int not null,
    phone varchar(15) not null,
    email TEXT not null,
    address TEXT not null,
    title TEXT not null,
    linkedin TEXT null,
    github TEXT null,
    facebook TEXT null,
    twitter TEXT null
);

create table presentation
(
    pres_id bigint auto_increment,
    constraint users_pk
        primary key (id),
    pres_title TEXT not null,
    description TEXT not null
);

create table experience
(
    exp_id bigint auto_increment,
    constraint users_pk
        primary key (id),
    exp_title TEXT not null,
    company_name TEXT not null,
    exp_location TEXT not null,
    start_date date not null,
    end_date date null,
    exp_description TEXT not null
);

create table education
(
    id bigint auto_increment,
    constraint users_pk
        primary key (id),
    edu_title TEXT not null,
    edu_name TEXT not null,
    edu_location TEXT not null,
    start_date date not null,
    end_date date null,
    edu_description TEXT not null
);

create table skills
(
    id bigint auto_increment,
    constraint users_pk
        primary key (id),
    skill_name TEXT not null,
    skill_grade TEXT not null
);

create table projects
(
    id bigint auto_increment,
    constraint users_pk
        primary key (id),
    proj_title varchar not null,
    proj_date date null,
    proj_description TEXT not null
);

create table hobbies
(
    id bigint auto_increment,
    constraint users_pk
        primary key (id),
    hob_title TEXT not null,
    hob_details TEXT not null
);

