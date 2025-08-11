package br.gov.ce.sefaz.chati;

import br.com.pocketbase.generic.BaseCrudService;
import br.com.pocketbase.http.PocketbaseCliente;
import br.com.pocketbase.seguranca.PocketbaseArmazenamentoCredencial;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
public class ChatPocketbaseService extends BaseCrudService<ChatRegistro> {

    public ChatPocketbaseService(PocketbaseCliente client, PocketbaseArmazenamentoCredencial segurancaService, String nomeColecao) {
        super(client, segurancaService, nomeColecao);
    }

    @Override
    public Class<ChatRegistro> getClassRegistro() {
        return ChatRegistro.class;
    }

    @Override
    public void preAtualizar(ChatRegistro t) {
        validar(t);
    }

    @Override
    public void preCriar(ChatRegistro t) {
        validar(t);
    }

    private void validar(ChatRegistro registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.nonNull(registro.getId()) && registro.getId().isBlank()) {
            registro.setId(null);
        }

        if (Objects.isNull(registro.getTitulo()) || registro.getTitulo().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }

        if (Objects.isNull(registro.getUrl())) {
            throw new RuntimeException("Dados inválidos");
        }
    }

}
