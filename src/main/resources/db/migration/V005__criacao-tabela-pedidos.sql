CREATE TABLE PEDIDOS (
	ID BIGINT NOT NULL AUTO_INCREMENT,

	USUARIOS_ID BIGINT NOT NULL,
	RESTAURANTES_ID BIGINT NOT NULL,
	FORMAS_PAGAMENTO_ID BIGINT NOT NULL,

	SUB_TOTAL DECIMAL(10,2) NOT NULL,
	TAXA_FRETE DECIMAL(10,2) NOT NULL,
	VALOR_TOTAL DECIMAL(10,2) NOT NULL,
	DATA_CRIACAO DATETIME(0) NOT NULL,
	DATA_CONFIRMACAO DATETIME(0),
	DATA_CANCELAMENTO DATETIME(0),
	DATA_ENTREGA DATETIME(0),
	STATUS_PEDIDO INTEGER,

	ENDERECO_CIDADE_ID BIGINT NOT NULL,
	ENDERECO_CEP VARCHAR(9) NOT NULL,
	ENDERECO_LOGRADOURO VARCHAR(100) NOT NULL,
	ENDERECO_NUMERO VARCHAR(20) NOT NULL,
	ENDERECO_COMPLEMENTO VARCHAR(60) NOT NULL,
	ENDERECO_BAIRRO VARCHAR(60) NOT NULL,

	PRIMARY KEY (ID),
	CONSTRAINT FK_PEDIDOS_RESTAURANTES FOREIGN KEY (RESTAURANTES_ID) REFERENCES RESTAURANTES(ID),
	CONSTRAINT FK_PEDIDOS_USUARIOS FOREIGN KEY (USUARIOS_ID) REFERENCES USUARIOS(ID),
	CONSTRAINT FK_PEDIDOS_FORMAS_PAGAMENTO FOREIGN KEY (FORMAS_PAGAMENTO_ID) REFERENCES FORMAS_PAGAMENTO(ID),
	CONSTRAINT FK_PEDIDOS_ENDERECO_CIDADES FOREIGN KEY (ENDERECO_CIDADE_ID) REFERENCES CIDADES(ID)
);
