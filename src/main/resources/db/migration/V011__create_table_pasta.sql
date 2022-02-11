CREATE TABLE IF NOT EXISTS pasta(
  id serial not null primary key,
  nome varchar(150) not null unique
);