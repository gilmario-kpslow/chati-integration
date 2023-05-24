package br.gov.ce.sefaz.chati.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gilmario
 */
@Provider
public class ExceptionHadler implements ExceptionMapper<RuntimeException> {

    private static final Logger LOG = Logger.getLogger(ExceptionHadler.class.getName());

    @Override
//    @Produces(value = MediaType.TEXT_PLAIN)
    public Response toResponse(RuntimeException e) {
        LOG.log(Level.SEVERE, "ERROR", e);
        return Response.status(Response.Status.BAD_REQUEST).
                entity(new ErrorMessage(e.getMessage())).build();
    }

}
