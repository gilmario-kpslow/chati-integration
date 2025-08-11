package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.pocketbase.PocketbaseService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author gilmario
 */
@Dependent
public class GrupoService {

    PocketbaseGrupoService service;

    @Inject
    PocketbaseService pocketbaseService;

    @PostConstruct
    private void init() throws Exception {
        service = pocketbaseService.getProvider().getServiceInstance(PocketbaseGrupoService.class, "grupo");
    }

    public Grupo criarAtualizar(Grupo registro) throws Exception {
        return service.criarAtualizar(registro);
    }

    public void deletar(String id) throws Exception {
        service.deletar(id);
    }

    public List<Grupo> lista() throws Exception {
        return service.listarTodos();
    }
}
