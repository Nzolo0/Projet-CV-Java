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
    instagram TEXT,
    facebook TEXT
);

create table presentations
(
    id bigint auto_increment primary key,
    title TEXT,
    description TEXT
);

create table experiences
(
    id bigint auto_increment primary key,
    title TEXT,
    company_name TEXT,
    location TEXT,
    start_date date,
    end_date date,
    description TEXT
);

create table educations
(
    id bigint auto_increment primary key ,
    title TEXT,
    name TEXT,
    location TEXT,
    start_date year,
    end_date year,
    description TEXT
);

create table skills
(
    id bigint auto_increment primary key,
    name TEXT,
    grade TEXT
);

create table projects
(
    id bigint auto_increment primary key,
    title TEXT,
    date year,
    description TEXT
);

create table hobbies
(
    id bigint auto_increment primary key,
    title TEXT,
    details TEXT
);

