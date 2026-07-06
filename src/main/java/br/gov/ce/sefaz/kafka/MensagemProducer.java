package br.gov.ce.sefaz.kafka;

import jakarta.enterprise.context.Dependent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

/**
 *
 * @author gilmario
 */
@Dependent
public class MensagemProducer {

    @Channel("chati.mensagem")
    Emitter<String> mensagemEmitter;

    public void publicaMensagem(String mensagem) {
        mensagemEmitter.send(mensagem);
    }

}
