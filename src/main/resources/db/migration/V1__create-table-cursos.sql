CREATE TABLE cursos(
    id bigint NOT NULL auto_increment,
    nome VARCHAR(100) NOT NULL UNIQUE,
    periodo ENUM('MATUTINO','VESPERTINO','NOTURNO', 'INTEGRAL') NOT NULL,
    ativo boolean NOT NULL DEFAULT 1,

    PRIMARY KEY(id)
);