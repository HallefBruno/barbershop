create table usuario(
    id serial not null primary key,
    nome varchar(100) not null,
    telefone varchar(11) not null,
    email varchar(200) not null unique,
    senha varchar(100) not null,
    ativo boolean not null,
    data_nascimento date not null,
    proprietario boolean not null,
    nome_foto varchar(100) not null unique,
    extensao varchar(8) not null,
    cliente_sistema_id int8 not null,
    CONSTRAINT cliente_sistema_fkey
      FOREIGN KEY(cliente_sistema_id) 
	  REFERENCES cliente_sistema(id)
);