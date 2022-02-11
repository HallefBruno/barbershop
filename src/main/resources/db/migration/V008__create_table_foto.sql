CREATE TABLE IF NOT EXISTS foto(
  id serial not null primary key,
  cpf_cnpj varchar(20) not null unique,
  nome_foto varchar(100) not null unique,
  extensao varchar(5) not null
);