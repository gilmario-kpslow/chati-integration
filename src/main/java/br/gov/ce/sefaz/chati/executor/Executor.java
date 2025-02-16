package br.gov.ce.sefaz.chati.executor;

import br.gov.ce.sefaz.chati.ChatRegistro;

/**
 *
 * @author gilmario
 */
public interface Executor {

    void notificar(ChatRegistro chati, String mensagem);

}
