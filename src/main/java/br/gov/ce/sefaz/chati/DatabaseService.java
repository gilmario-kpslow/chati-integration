/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz.chati;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @author gilmario
 */
@Dependent
public class DatabaseService {

    @Inject
    MongoClient mongoClient;

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("chati").getCollection("chat_registro");
    }

    public List<ChatRegistro> lista() {
        List<ChatRegistro> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(parse(cursor.next()));
            }
        }
        return lista;
    }

    public ChatRegistro getByChave(String chave) {
        ChatRegistro find = null;
        try (MongoCursor<Document> cursor = getCollection().find(new Document().append("id", chave)).iterator()) {
            if (cursor.hasNext()) {
                find = parse(cursor.next());
            }
        }
        return find;
    }

    ChatRegistro save(ChatRegistro registro) {
        Document document = new Document()
                .append("titulo", registro.getTitulo())
                .append("mensagem", registro.getMensagem())
                .append("id", registro.getId())
                .append("url", registro.getUrl());
        getCollection().insertOne(document);

        return registro;
    }

    ChatRegistro update(ChatRegistro registro) {
        Document document = new Document()
                .append("titulo", registro.getTitulo())
                .append("mensagem", registro.getMensagem())
                .append("id", registro.getId())
                .append("url", registro.getUrl());

        getCollection().replaceOne(new Document().append("id", registro.getId()), document);

        return registro;
    }

    public ChatRegistro parse(Document document) {
        ChatRegistro registro = new ChatRegistro();
        registro.setTitulo(document.getString("titulo"));
        registro.setMensagem(document.getString("mensagem"));
        registro.setId(document.getString("id"));
        registro.setUrl(document.getString("url"));
        return registro;
    }

    public void delete(String chave) {
        getCollection().deleteOne(new Document("id", chave));
    }

}
