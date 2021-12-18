package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.pedido.PedidoRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    public PedidoController(PedidoRepository pedidoRepository,
                            PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }


    @GetMapping
    @ResponseMappedEntity(mappedClass = PedidoRepresentation.ListagemResumida.class)
    public List<Pedido> listAll() {
        return pedidoRepository.findAll();
    }

    @GetMapping("{id}")
    @ResponseMappedEntity(mappedClass = PedidoRepresentation.Listagem.class)
    public Pedido find(@PathVariable Long id) {
        return pedidoRepository.buscarOuFalhar(id);
    }

    @PostMapping
    @RequestMappedEntity(mappedClass = PedidoRepresentation.Completa.class)
    public void emitir(@RequestBody @Valid Pedido pedido) {
        pedidoService.emitir(pedido);
    }
}
