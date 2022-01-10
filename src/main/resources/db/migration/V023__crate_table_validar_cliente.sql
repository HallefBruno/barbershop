CREATE TABLE IF NOT EXISTS validar_cliente(
    id serial not null primary key,
    data_validacao timestamp NOT NULL,
    cpf_cnpj varchar(20) not null unique,
    conta_criada BOOLEAN DEFAULT FALSE
);