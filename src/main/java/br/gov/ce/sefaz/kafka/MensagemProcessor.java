package br.gov.ce.sefaz.kafka;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class MensagemProcessor {

    @Incoming("chat-mensagem")
//    @Outgoing("quotes")
    @Blocking
    public CompletionStage<Void> process(Message<String> msg) throws InterruptedException {
        // simulate some hard working task
        Thread.sleep(200);
//        return new Quote(quoteRequest, random.nextInt(100));
        System.out.println("Mensagem recebida: " + msg.getPayload());

//        return msg.ack();
        return msg.nack(new RuntimeException("OK"));
    }
}
