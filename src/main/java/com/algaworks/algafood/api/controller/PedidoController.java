package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.pedido.PedidoRepresentation;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    //    private final ProdutoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {

        this.pedidoRepository = pedidoRepository;
    }


    @GetMapping
    @ResponseMappedEntity(mappedClass = PedidoRepresentation.Listagem.class)
    public List<Pedido> listAll() {
        return pedidoRepository.findAll();
    }

    @GetMapping("{id}")
    @ResponseMappedEntity(mappedClass = PedidoRepresentation.Listagem.class)
    public Pedido find(@PathVariable Long id) {
        return pedidoRepository.buscarOuFalhar(id);
    }
}
