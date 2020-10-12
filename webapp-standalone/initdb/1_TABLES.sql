create table users
(
    id bigint auto_increment primary key,
    first_name TEXT not null ,
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
    pres_id bigint auto_increment primary key,
    pres_title TEXT not null,
    description TEXT not null
);

create table experience
(
    exp_id bigint auto_increment primary key,
    exp_title TEXT not null,
    company_name TEXT not null,
    exp_location TEXT not null,
    start_date date not null,
    end_date date null,
    exp_description TEXT not null
);

create table education
(
    ed_id bigint auto_increment primary key ,
    edu_title TEXT not null,
    edu_name TEXT not null,
    edu_location TEXT not null,
    start_date date not null,
    end_date date null,
    edu_description TEXT not null
);

create table skills
(
    skill_id bigint auto_increment primary key,
    skill_name TEXT not null,
    skill_grade TEXT not null
);

create table projects
(
    proj_id bigint auto_increment primary key,
    proj_title TEXT not null,
    proj_date date null,
    proj_description TEXT not null
);

create table hobbies
(
    hob_id bigint auto_increment primary key,
    hob_title TEXT not null,
    hob_details TEXT not null
);

