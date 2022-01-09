create table cliente(
    id serial not null primary key,
    nome varchar(100) not null,
    telefone varchar(11) not null,
    email varchar(200) not null unique,
    nome_foto varchar(100) not null unique,
    extensao varchar(8) not null,
    data_nascimento date not null
);