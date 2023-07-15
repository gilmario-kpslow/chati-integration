package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.NoDatabaseService;
import javax.inject.Singleton;

/**
 *
 * @author gilmario
 */
@Singleton
public class NoDatabaseChatRegistroService extends NoDatabaseService<ChatRegistro, String> {

    @Override
    public ChatRegistro save(ChatRegistro registro) {
        return super.save(registro);
    }

}
