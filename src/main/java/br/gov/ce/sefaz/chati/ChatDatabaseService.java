/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import com.mongodb.client.MongoCursor;
import javax.enterprise.context.Dependent;
import org.bson.Document;

/**
 *
 * @author gilmario
 */
@Dependent
public class ChatDatabaseService extends DatabaseService<String, ChatRegistro> {

    public ChatDatabaseService() {
        super("chat-registro");
    }

    public ChatRegistro getByChave(String chave) throws Exception {
        ChatRegistro find = null;
        try (MongoCursor<Document> cursor = getCollection().find(new Document().append("id", chave)).iterator()) {
            if (cursor.hasNext()) {
                find = parse(cursor.next());
            }
        }
        return find;
    }

    @Override
    protected Class<ChatRegistro> getEntityClass() {
        return ChatRegistro.class;
    }
}
