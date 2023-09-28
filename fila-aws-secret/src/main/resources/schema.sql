CREATE TABLE cliente
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid          UUID,
    tipo_cliente  VARCHAR(255),
    mcc           VARCHAR(4)                              NOT NULL,
    cnpj          VARCHAR(14),
    razao_social  VARCHAR(50),
    cpf_contato   VARCHAR(11),
    nome_contato  VARCHAR(50),
    email_contato VARCHAR(255),
    cpf_pessoa    VARCHAR(11),
    nome_pessoa   VARCHAR(50),
    email_pessoa  VARCHAR(255),
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);