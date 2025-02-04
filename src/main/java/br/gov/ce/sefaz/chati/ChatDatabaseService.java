package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author gilmario
 */
@Dependent
public class ChatDatabaseService extends DatabaseService<ChatRegistro> {

    public ChatDatabaseService() {
        super("chatregistro");
    }

}
