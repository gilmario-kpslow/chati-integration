package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.NoDatabaseService;
import javax.inject.Singleton;

/**
 *
 * @author gilmario
 */
@Singleton
public class NoDatabaseGrupoService extends NoDatabaseService<Grupo, String> {

    @Override
    public Grupo save(Grupo registro) {
        return super.save(registro);
    }

}
