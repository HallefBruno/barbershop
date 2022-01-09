create table permissao(
    id serial not null primary key,
    nome varchar(100) not null unique
);