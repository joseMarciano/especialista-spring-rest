insert into cozinhas (id, nome) values (1, 'Tailandesa');
insert into cozinhas (id, nome) values (2, 'Indiana');

insert into estados (id, nome) values (1, 'Minas Gerais');
insert into estados (id, nome) values (2, 'São Paulo');
insert into estados (id, nome) values (3, 'Ceará');

insert into cidades (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidades (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidades (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidades (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidades (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurantes (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values (1, 'Thai Gourmet', 10, 1,1,'88868-0000','Rua fre elizeu','1000','Centro', utc_timestamp, utc_timestamp);
insert into restaurantes (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (2, 'Thai Delivery', 9.50, 1,utc_timestamp,utc_timestamp);
insert into restaurantes (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Tuk Tuk Comida Indiana', 15, 2,utc_timestamp,utc_timestamp);


insert into formas_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into formas_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into formas_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissoes (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissoes (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento(restaurante_id,forma_pagamento_id) values (1,1),(1,2),(1,2),(2,3),(3,2),(3,3);