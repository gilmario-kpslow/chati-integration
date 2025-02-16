package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import jakarta.enterprise.context.Dependent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@Dependent
public class ChatDatabaseService extends DatabaseService<ChatRegistro> {

    @ConfigProperty(name = "app.pocketbase.collection")
    String collection;

    @Override
    protected Class<ChatRegistro> getClassEntity() {
        return ChatRegistro.class;
    }

    @Override
    protected String getEntityName() {
        return collection;
    }

}
