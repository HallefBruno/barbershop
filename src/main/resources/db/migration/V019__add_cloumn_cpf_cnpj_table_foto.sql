ALTER TABLE foto
ADD COLUMN IF NOT EXISTS cpf_cnpj varchar(20) not null;