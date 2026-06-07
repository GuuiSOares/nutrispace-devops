INSERT INTO tb_ns_astronauta (id_astronauta, nome, cargo, email, senha)
SELECT 1, 'Lucas Silva', 'Engenheiro de Software Chefe', 'lucas@nutrispace.com', 'senha123'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM tb_ns_astronauta WHERE email = 'lucas@nutrispace.com');
