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

    @Override
    protected Class<Grupo> getEntityClass() {
        return Grupo.class;
    }
}
