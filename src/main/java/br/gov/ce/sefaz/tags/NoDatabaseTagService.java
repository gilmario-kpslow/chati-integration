package br.gov.ce.sefaz.tags;

import br.gov.ce.sefaz.chati.core.NoDatabaseService;
import javax.inject.Singleton;

/**
 *
 * @author gilmario
 */
@Singleton
public class NoDatabaseTagService extends NoDatabaseService<Tag, String> {

    @Override
    public Tag save(Tag registro) {
        return super.save(registro);
    }

}
