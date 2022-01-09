CREATE TABLE cliente_sistema (
    id bigserial NOT NULL,
    bairro varchar(255) NOT NULL,
    cep varchar(255) NOT NULL,
    cidade varchar(255) NOT NULL,
    cpf_cnpj varchar(255) NOT NULL unique,
    data_atualizacao timestamp NOT NULL,
    data_cadastro timestamp NOT NULL,
    estado varchar(255) NOT NULL,
    logradouro varchar(255) NOT NULL,
    nome_comercio varchar(255) NOT NULL,
    ativo boolean not null,
    qtd_usuario int8 NOT NULL,
    CONSTRAINT cliente_sistema_pkey PRIMARY KEY (id)
);