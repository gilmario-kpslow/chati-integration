package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.GenericService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class GrupoService extends GenericService<Grupo> {

    @Inject
    DatabaseGrupoService databaseService;

    @Override
    protected void validar(Grupo registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.isNull(registro.getNome()) || registro.getNome().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }
    }

    @Override
    protected DatabaseGrupoService getDatabaseService() {
        return this.databaseService;
    }

}
