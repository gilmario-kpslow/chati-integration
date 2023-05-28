package br.gov.ce.sefaz.chati;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                Document document = cursor.next();
                ChatRegistro registro = new ChatRegistro();
                registro.setTitulo(document.getString("titulo"));
                registro.setMensagem(document.getString("mensagem"));
                registro.setId(document.getString("id"));
                registro.setUrl(document.getString("url"));
                lista.add(registro);
            }
        }
        return lista;
    }

    public void save(ChatRegistro registro) {
        validar(registro);
        Document document = new Document()
                .append("titulo", registro.getTitulo())
                .append("mensagem", registro.getMensagem())
                .append("id", registro.getId())
                .append("url", registro.getUrl());
        getCollection().insertOne(document);
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
        return lista().stream().filter(p -> p.getId().equals(chave)).findFirst().orElseThrow(() -> new RuntimeException("Chave não encontrada"));
    }
}
