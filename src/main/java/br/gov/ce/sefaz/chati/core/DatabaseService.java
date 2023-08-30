package br.gov.ce.sefaz.chati.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    String databaseName;

    @Inject
    MongoClient mongoClient;

    private final String entityName;

    public DatabaseService(String entity) {
        this.entityName = entity;
    }

    protected MongoCollection getCollection() {
        return mongoClient.getDatabase(databaseName).getCollection(this.entityName);
    }

    public List<K> lista() throws Exception {
        List<K> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(parse(cursor.next()));
            }
        }
        return lista;
    }

    public K save(K registro) throws Exception {
        Document document = from(registro);
        getCollection().insertOne(document);
        return registro;
    }

    public K update(K registro) throws Exception {
        Document document = from(registro);
        getCollection().replaceOne(new Document().append("id", registro.getId()), document);
        return registro;
    }

    protected abstract Class<K> getEntityClass();

    protected K parse(Document document) throws Exception {
        K k = getEntityClass().getConstructor().newInstance();
        Field[] fields = getEntityClass().getDeclaredFields();
        for (Field field : fields) {

            Method metodo = getEntityClass().getDeclaredMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), String.class);
            metodo.invoke(k, document.get(field.getName()));

        }
        return k;
    }

    protected Document from(K entity) throws Exception {
        Field[] fields = entity.getClass().getDeclaredFields();
        Document document = new Document();

        for (Field field : fields) {
            Method metodo = getEntityClass().getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
            document.append(field.getName(), metodo.invoke(entity));
        }

        return document;
    }

    public void delete(P id) {
        getCollection().deleteOne(new Document("id", id));
    }
}
