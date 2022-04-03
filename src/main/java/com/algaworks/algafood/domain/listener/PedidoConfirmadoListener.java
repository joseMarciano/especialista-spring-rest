package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EmailSenderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;

import static com.algaworks.algafood.domain.service.EmailSenderService.*;

@Component
public class PedidoConfirmadoListener {

    private final EmailSenderService emailSenderService;

    public PedidoConfirmadoListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }


//    @EventListener
    @TransactionalEventListener // nesse caso devemos tomar cuidado pois será disparado o event depois do commit e não tera rollback em casos de error no evento (podemos sleecionar fases como BEFORE_COMMIT )
    public void onPedidoConfirmado(PedidoConfirmadoEvent event) {
//        event.getPedido()
        emailSenderService.send(Message.builder()
                .subject("AlgaFood")
                .body("\"Pedido emitido com sucesso!!\"")
                .destinations(Collections.singleton("marciano13@gmail.com"))
                .build());
    }
}
