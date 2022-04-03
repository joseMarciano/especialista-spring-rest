package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EmailSenderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.algaworks.algafood.domain.service.EmailSenderService.*;

@Component
public class PedidoConfirmadoListener {

    private final EmailSenderService emailSenderService;

    public PedidoConfirmadoListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }


    @EventListener
    public void onPedidoConfirmado(PedidoConfirmadoEvent event) {
//        event.getPedido()
        emailSenderService.send(Message.builder()
                .subject("AlgaFood")
                .body("\"Pedido emitido com sucesso!!\"")
                .destinations(Collections.singleton("marciano13@gmail.com"))
                .build());
    }
}
