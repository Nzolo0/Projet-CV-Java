create table users
(
    id bigint auto_increment primary key,
    first_name TEXT,
    last_name TEXT,
    age int,
    phone varchar(15),
    email TEXT,
    address TEXT,
    title TEXT,
    linkedin TEXT,
    github TEXT,
    facebook TEXT
);

create table presentation
(
    pres_id bigint auto_increment primary key,
    pres_title TEXT,
    description TEXT
);

create table experience
(
    exp_id bigint auto_increment primary key,
    exp_title TEXT,
    company_name TEXT,
    exp_location TEXT,
    start_date date,
    end_date date,
    exp_description TEXT
);

create table education
(
    ed_id bigint auto_increment primary key ,
    edu_title TEXT,
    edu_name TEXT,
    edu_location TEXT,
    start_date year,
    end_date year,
    edu_description TEXT
);

create table skills
(
    skill_id bigint auto_increment primary key,
    skill_name TEXT,
    skill_grade TEXT
);

create table projects
(
    proj_id bigint auto_increment primary key,
    proj_title TEXT,
    proj_date year,
    proj_description TEXT
);

create table hobbies
(
    hob_id bigint auto_increment primary key,
    hob_title TEXT,
    hob_details TEXT
);

