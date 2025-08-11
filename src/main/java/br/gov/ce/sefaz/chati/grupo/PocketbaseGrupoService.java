package br.gov.ce.sefaz.chati.grupo;

import br.com.pocketbase.generic.BaseCrudService;
import br.com.pocketbase.http.PocketbaseCliente;
import br.com.pocketbase.seguranca.PocketbaseArmazenamentoCredencial;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
public class PocketbaseGrupoService extends BaseCrudService<Grupo> {

    public PocketbaseGrupoService(PocketbaseCliente client, PocketbaseArmazenamentoCredencial segurancaService, String nomeColecao) {
        super(client, segurancaService, nomeColecao);
    }

    @Override
    public Class<Grupo> getClassRegistro() {
        return Grupo.class;
    }

    private void validar(Grupo registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.isNull(registro.getNome()) || registro.getNome().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }
    }

    @Override
    public void preAtualizar(Grupo t) {
        validar(t);
    }

    @Override
    public void preCriar(Grupo t) {
        validar(t);
    }

}
