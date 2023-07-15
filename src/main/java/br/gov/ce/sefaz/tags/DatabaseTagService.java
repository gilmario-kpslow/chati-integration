package br.gov.ce.sefaz.tags;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import javax.enterprise.context.Dependent;
import org.bson.Document;

/**
 *
 * @author gilmario
 */
@Dependent
public class DatabaseTagService extends DatabaseService<String, Tag> {

    public DatabaseTagService() {
        super("tag");
    }

    @Override
    public Tag parse(Document document) {
        Tag registro = new Tag();
        registro.setCor(document.getString("cor"));
        registro.setId(document.getString("id"));
        registro.setNome(document.getString("nome"));
        return registro;
    }

    @Override
    protected Document from(Tag entity) {
        Document document = new Document()
                .append("id", entity.getId())
                .append("nome", entity.getNome())
                .append("cor", entity.getCor());
        return document;
    }
}
