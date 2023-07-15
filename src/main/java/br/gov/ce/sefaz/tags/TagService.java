package br.gov.ce.sefaz.tags;

import br.gov.ce.sefaz.chati.core.GenericService;
import java.util.Objects;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class TagService extends GenericService<String, Tag> {

    @Inject
    DatabaseTagService databaseService;

    @Inject
    NoDatabaseTagService noDatabaseService;

    @Override
    protected void validar(Tag registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.isNull(registro.getNome()) || registro.getNome().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }
    }

    @Override
    protected Tag save(Tag registro) {
        registro.setId(UUID.randomUUID().toString());
        return super.save(registro);
    }

    @Override
    protected DatabaseTagService getDatabaseService() {
        return this.databaseService;
    }

    @Override
    protected NoDatabaseTagService getNoDatabaseService() {
        return this.noDatabaseService;
    }

}
