create table users (
  id int not null,
  name varchar(100) not null,
  country varchar(100) not null,
  city varchar(100) not null,
  email varchar(100) not null,
  login varchar(100) not null,
  password varchar(100) not null,
  photoid varchar(100) not null,
  date_of_creation varchar(100) not null,
  role varchar(100) not null
);

select * from users;

delete from users;

drop table users;