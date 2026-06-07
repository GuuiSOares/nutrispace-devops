-- DevOps: complementa tabelas que o Hibernate pode nao criar no Oracle XE em container
CREATE SEQUENCE seq_ns_planta START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE tb_ns_planta (
    id_planta       NUMBER(19,0) NOT NULL PRIMARY KEY,
    nome_planta     VARCHAR2(80),
    temp_min_ideal  NUMBER(19,2),
    temp_max_ideal  NUMBER(19,2),
    umi_min_ideal   NUMBER(19,2)
);

CREATE SEQUENCE seq_ns_reservatorio START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE tb_ns_reservatorio (
    id_reservatorio          NUMBER(19,0) NOT NULL PRIMARY KEY,
    capacidade_max_litros    NUMBER(19,2),
    nivel_atual_percentual   NUMBER(19,2),
    id_estufa                NUMBER(19,0) NOT NULL
);

CREATE SEQUENCE seq_ns_leitor START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE tb_ns_leitura_sensor (
    id_leitor         NUMBER(19,0) NOT NULL PRIMARY KEY,
    id_estufa         NUMBER(19,0) NOT NULL,
    temperatura_lida  NUMBER(19,2),
    umidade_lida      NUMBER(19,2),
    dt_hr_leitura     TIMESTAMP(6)
);

CREATE SEQUENCE seq_ns_colheita START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE tb_ns_colheita (
    id_colheita    NUMBER(19,0) NOT NULL PRIMARY KEY,
    id_estufa      NUMBER(19,0) NOT NULL,
    quantidade_kg  NUMBER(19,2),
    dt_colheita    TIMESTAMP(6),
    qualidade      VARCHAR2(20)
);
