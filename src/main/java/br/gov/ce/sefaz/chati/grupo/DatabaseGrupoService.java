package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author gilmario
 */
@Dependent
public class DatabaseGrupoService extends DatabaseService<Grupo> {

    public DatabaseGrupoService() {
        super("grupo");
    }

    @Override
    protected Class<Grupo> getClassEntity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
