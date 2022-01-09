CREATE TABLE grupo_permissao (
    id_grupo int8 NOT NULL,
    id_permissao int8 NOT NULL,
    CONSTRAINT grupo_permissao_pkey PRIMARY KEY (id_grupo, id_permissao)
);