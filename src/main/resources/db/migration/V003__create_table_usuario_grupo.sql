CREATE TABLE usuario_grupo (
    id_usuario int8 NOT NULL,
    id_grupo int8 NOT NULL,
    CONSTRAINT usuario_grupo_pkey PRIMARY KEY (id_usuario, id_grupo)
);