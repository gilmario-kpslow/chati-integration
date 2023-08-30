package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import javax.enterprise.context.Dependent;

/**
 *
 * @author gilmario
 */
@Dependent
public class DatabaseGrupoService extends DatabaseService<String, Grupo> {

    public DatabaseGrupoService() {
        super("grupo");
    }

//    @Override
//    public Grupo parse(Document document) {
//        Grupo registro = new Grupo();
//        registro.setCor(document.getString("cor"));
//        registro.setId(document.getString("id"));
//        registro.setNome(document.getString("nome"));
//        return registro;
//    }
//
//    @Override
//    protected Document from(Grupo entity) {
//        Document document = new Document()
//                .append("id", entity.getId())
//                .append("nome", entity.getNome())
//                .append("cor", entity.getCor());
//        return document;
//    }
    @Override
    protected Class<Grupo> getEntityClass() {
        return Grupo.class;
    }
}
