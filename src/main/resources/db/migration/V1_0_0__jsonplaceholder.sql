create table user
(
    id                   bigint       not null,
    name                 varchar(100) not null,
    city                 varchar(255),
    company_bs           varchar(255),
    company_catch_phrase varchar(255),
    company_name         varchar(255),
    geo_lat              varchar(255),
    geo_lng              varchar(255),
    phone                varchar(255) not null,
    street               varchar(255),
    username             varchar(255) not null,
    website              varchar(255) not null,
    zipcode              varchar(255),
    primary key (id)
) engine=InnoDB;

create table todo
(
    completed bit           not null,
    id        bigint        not null,
    user_id   bigint,
    title     varchar(1000) not null,
    primary key (id),
    foreign key (user_id) references user (id)
) engine=InnoDB;

create table post
(
    id      bigint        not null,
    user_id bigint        not null,
    body    varchar(1000) not null,
    title   varchar(1000) not null,
    primary key (id),
    foreign key (user_id) references user (id)
) engine=InnoDB;

create table comments
(
    id      bigint       not null,
    post_id bigint       not null,
    email   varchar(100) not null,
    body    varchar(500) not null,
    primary key (id),
    foreign key (post_id) references post (id)
) engine=InnoDB;


