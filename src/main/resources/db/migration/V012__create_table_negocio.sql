CREATE TABLE IF NOT EXISTS negocio(
  id serial not null primary key,
  nome varchar(100) not null unique
);