package br.gov.ce.sefaz.chati;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import org.bson.Document;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatService {

    @Inject
    MongoClient mongoClient;

    @Inject
    ChatExecutor executor;

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

    public ChatRegistro saveOrUpdate(ChatRegistro registro) {
        if (Objects.isNull(registro.getId())) {
            return this.save(registro);
        } else {
            return this.update(registro);
        }
    }

    private ChatRegistro save(ChatRegistro registro) {
        registro.setId(UUID.randomUUID().toString());
        validar(registro);
        Document document = new Document()
                .append("titulo", registro.getTitulo())
                .append("mensagem", registro.getMensagem())
                .append("id", registro.getId())
                .append("url", registro.getUrl());
        getCollection().insertOne(document);

        return registro;
    }

    private ChatRegistro update(ChatRegistro registro) {
        validar(registro);
        Document document = new Document()
                .append("titulo", registro.getTitulo())
                .append("mensagem", registro.getMensagem())
                .append("id", registro.getId())
                .append("url", registro.getUrl());

        getCollection().replaceOne(new Document().append("id", registro.getId()), document);

        return registro;
    }

    private void validar(ChatRegistro registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.isNull(registro.getTitulo()) || registro.getTitulo().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }
    }

    public void execute(String chave, MultivaluedMap<String, String> values) {
        ChatRegistro dto = getByChave(chave);

//        StringBuilder mensagemFormatada = new StringBuilder(dto.getMensagem());
        String mesagem = dto.getMensagem();
        if (Objects.nonNull(values)) {
            for (Map.Entry<String, List<String>> entry : values.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                mesagem = mesagem.replaceAll("\\$\\{" + key + "\\}", value.get(0));
            }
        }
        System.out.println(mesagem);
        executor.executar(dto.getUrl(), mesagem);
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
