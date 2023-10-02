package br.gov.ce.sefaz.chati.fileserve;

import br.gov.ce.sefaz.chati.ChatRegistro;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author gilmario
 */
@Path("file-server")
public class FileServerResource {

    @Inject
    private FileService service;

    @GET
    public List<String> listarBakups() throws IOException {
        return service.list();
    }

    @POST
    public void save(List<ChatRegistro> lista) {
        service.save(lista);
    }
}
