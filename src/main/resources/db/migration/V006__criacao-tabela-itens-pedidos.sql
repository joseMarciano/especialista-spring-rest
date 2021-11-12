CREATE TABLE ITENS_PEDIDO(
	ID BIGINT NOT NULL AUTO_INCREMENT,

	PRODUTOS_ID BIGINT NOT NULL,
	PEDIDOS_ID BIGINT NOT NULL,

	QUANTIDADE INTEGER NOT NULL,
	PRECO_UNITARIO DECIMAL(10,2) NOT NULL,
	PRECO_TOTAL DECIMAL(10,2) NOT NULL,
	OBSERVACAO VARCHAR(100),

	PRIMARY KEY (ID),
	UNIQUE KEY UK_ITENS_PEDIDO_PRODUTO (PRODUTOS_ID, PEDIDOS_ID),
	CONSTRAINT FK_ITENS_PEDIDO_PRODUTOS FOREIGN KEY (PRODUTOS_ID) REFERENCES PRODUTOS(ID),
	CONSTRAINT FK_ITENS_PEDIDO_PEDIDOS FOREIGN KEY (PEDIDOS_ID) REFERENCES PEDIDOS(ID)
);