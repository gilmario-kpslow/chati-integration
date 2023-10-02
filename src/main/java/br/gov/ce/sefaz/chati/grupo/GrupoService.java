package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.GenericService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class GrupoService extends GenericService<String, Grupo> {

    @Inject
    DatabaseGrupoService databaseService;

    @Inject
    NoDatabaseGrupoService noDatabaseService;

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
    protected Grupo save(Grupo registro) throws Exception {
        registro.setId(UUID.randomUUID().toString());
        return super.save(registro);
    }

    @Override
    protected DatabaseGrupoService getDatabaseService() {
        return this.databaseService;
    }

    @Override
    protected NoDatabaseGrupoService getNoDatabaseService() {
        return this.noDatabaseService;
    }

}
