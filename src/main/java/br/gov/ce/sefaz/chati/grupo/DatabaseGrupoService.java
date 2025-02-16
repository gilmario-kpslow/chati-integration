package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author gilmario
 */
@Dependent
public class DatabaseGrupoService extends DatabaseService<Grupo> {

    @Override
    protected Class<Grupo> getClassEntity() {
        return Grupo.class;
    }

    @Override
    protected String getEntityName() {
        return "grupo";
    }

}
