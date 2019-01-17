DROP TABLE IF EXISTS user_authority;
create table user_authority
(
  id   int4 auto_increment,
  name varchar(255) not null,
  constraint user_authority_pk
    primary key (id)
);

DROP TABLE IF EXISTS user_authority_info;
create table user_authority_info
(
  id           int4 auto_increment,
  user_id      int4 not null,
  authority_id int4 not null,
  constraint user_authority_info_pk
    primary key (id)
);

DROP TABLE IF EXISTS user_info;
create table user_info
(
  id          int4 auto_increment,
  username    varchar(255) not null,
  password    varchar(255) not null,
  enabled     bool         not null,
  update_time date         not null,
  constraint user_info_pk
    primary key (id)
);



