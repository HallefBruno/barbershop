CREATE TABLE IF NOT EXISTS grupo (
  id bigserial NOT NULL,
  nome varchar(255) NULL unique,
  CONSTRAINT grupo_pkey PRIMARY KEY (id)
);