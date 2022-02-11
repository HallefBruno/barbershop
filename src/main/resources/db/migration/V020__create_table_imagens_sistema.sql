CREATE TABLE IF NOT EXISTS imagens_sistema(
  id serial not null primary key,
  nome varchar(100) not null unique,
  extensao varchar(5) not null
);