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

//    @Override
//    public ChatRegistro parse(Document document, ChatRegistro chat) {
//        ChatRegistro registro = new ChatRegistro();
//        registro.setTitulo(document.getString("titulo"));
//        registro.setMensagem(document.getString("mensagem"));
//        registro.setId(document.getString("id"));
//        registro.setUrl(document.getString("url"));
//        registro.setCor(document.getString("cor"));
//        return registro;
//    }
//    @Override
//    protected Document from(ChatRegistro entity) {
//        Document document = new Document()
//                .append("titulo", entity.getTitulo())
//                .append("mensagem", entity.getMensagem())
//                .append("id", entity.getId())
//                .append("url", entity.getUrl())
//                .append("cor", entity.getCor());
//        return document;
//    }
    @Override
    protected Class<ChatRegistro> getEntityClass() {
        return ChatRegistro.class;
    }
}
