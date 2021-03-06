CREATE TABLE FORMAS_PAGAMENTO (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	DESCRICAO VARCHAR(60) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE GRUPOS (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(60) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE GRUPOS_PERMISSOES (
	GRUPOS_ID BIGINT NOT NULL,
	PERMISSOES_ID BIGINT NOT NULL,
	PRIMARY KEY (GRUPOS_ID, PERMISSOES_ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE PERMISSOES (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	DESCRICAO VARCHAR(60) NOT NULL,
	NOME VARCHAR(100) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE PRODUTOS (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	RESTAURANTES_ID BIGINT NOT NULL,
	NOME VARCHAR(80) NOT NULL,
	DESCRICAO TEXT NOT NULL,
	PRECO DECIMAL(10,2) NOT NULL,
	ATIVO TINYINT(1) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE RESTAURANTES (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	COZINHAS_ID BIGINT NOT NULL,
	NOME VARCHAR(80) NOT NULL,
	TAXA_FRETE DECIMAL(10,2) NOT NULL,
	DATA_ATUALIZACAO DATETIME NOT NULL,
	DATA_CADASTRO DATETIME NOT NULL,
	ENDERECO_CIDADE_ID BIGINT,
	ENDERECO_CEP VARCHAR(9),
	ENDERECO_LOGRADOURO VARCHAR(100),
	ENDERECO_NUMERO VARCHAR(20),
	ENDERECO_COMPLEMENTO VARCHAR(60),
	ENDERECO_BAIRRO VARCHAR(60),
	PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE RESTAURANTES_FORMAS_PAGAMENTO (
	RESTAURANTES_ID BIGINT NOT NULL,
	FORMAS_PAGAMENTO_ID BIGINT NOT NULL,
	PRIMARY KEY (RESTAURANTES_ID, FORMAS_PAGAMENTO_ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE USUARIOS (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(80) NOT NULL,
	EMAIL VARCHAR(255) NOT NULL,
	SENHA VARCHAR(255) NOT NULL,
	DATA_CADASTRO DATETIME NOT NULL,
	PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE USUARIOS_GRUPOS (
	USUARIOS_ID BIGINT NOT NULL,
	GRUPOS_ID BIGINT NOT NULL,
	PRIMARY KEY (USUARIOS_ID, GRUPOS_ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


ALTER TABLE GRUPOS_PERMISSOES ADD CONSTRAINT FK_GRUPOS_PERMISSOES_PERMISSOES
FOREIGN KEY (PERMISSOES_ID) REFERENCES PERMISSOES (ID);

ALTER TABLE GRUPOS_PERMISSOES ADD CONSTRAINT FK_GRUPOS_PERMISSOES_GRUPOS
FOREIGN KEY (GRUPOS_ID) REFERENCES GRUPOS (ID);

ALTER TABLE PRODUTOS ADD CONSTRAINT FK_PRODUTOS_RESTAURANTES
FOREIGN KEY (RESTAURANTES_ID) REFERENCES RESTAURANTES (ID);

ALTER TABLE RESTAURANTES ADD CONSTRAINT FK_RESTAURANTES_COZINHAS
FOREIGN KEY (COZINHAS_ID) REFERENCES COZINHAS (ID);

ALTER TABLE RESTAURANTES ADD CONSTRAINT FK_RESTAURANTES_CIDADE
FOREIGN KEY (ENDERECO_CIDADE_ID) REFERENCES CIDADES (ID);

ALTER TABLE RESTAURANTES_FORMAS_PAGAMENTO ADD CONSTRAINT FK_REST_FORMA_PAGTO_FORMA_PAGTO
FOREIGN KEY (FORMAS_PAGAMENTO_ID) REFERENCES FORMAS_PAGAMENTO (ID);

ALTER TABLE RESTAURANTES_FORMAS_PAGAMENTO ADD CONSTRAINT FK_REST_FORMA_PAGTO_RESTAURANTES
FOREIGN KEY (RESTAURANTES_ID) REFERENCES RESTAURANTES (ID);

ALTER TABLE USUARIOS_GRUPOS ADD CONSTRAINT FK_USUARIOS_GRUPOS_GRUPOS
FOREIGN KEY (GRUPOS_ID) REFERENCES GRUPOS (ID);

ALTER TABLE USUARIOS_GRUPOS ADD CONSTRAINT FK_USUARIOS_GRUPOS_USUARIOS
FOREIGN KEY (USUARIOS_ID) REFERENCES USUARIOS (ID);

ALTER TABLE CIDADES ADD CONSTRAINT FK_CIDADES_ESTADOS
FOREIGN KEY (ESTADOS_ID) REFERENCES CIDADES (ID);