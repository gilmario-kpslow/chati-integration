/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz.chati.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 * @param <P>
 * @param <K>
 */
public abstract class DatabaseService<P extends Serializable, K extends Entity<P>> {

    @ConfigProperty(name = "databasename")
    private String databaseName;

    @Inject
    private MongoClient mongoClient;

    private final String entityName;

    public DatabaseService(String entity) {
        this.entityName = entity;
    }

    protected MongoCollection getCollection() {
        return mongoClient.getDatabase(databaseName).getCollection(this.entityName);
    }

    public List<K> lista() {
        List<K> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(parse(cursor.next()));
            }
        }
        return lista;
    }

    public K save(K registro) {
        Document document = from(registro);
        getCollection().insertOne(document);
        return registro;
    }

    public K update(K registro) {
        Document document = from(registro);
        getCollection().replaceOne(new Document().append("id", registro.getId()), document);
        return registro;
    }

    protected abstract K parse(Document document);

    protected abstract Document from(K entity);

    public void delete(P id) {
        getCollection().deleteOne(new Document("id", id));
    }
}
