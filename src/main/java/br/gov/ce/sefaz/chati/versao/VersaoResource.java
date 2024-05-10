package br.gov.ce.sefaz.chati.versao;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ResourceBundle;

/**
 *
 * @author gilmario
 */
@Path(value = "versao")
public class VersaoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Versao versao() {
        ResourceBundle bundle = ResourceBundle.getBundle("api_info");
        String[] dataHora = bundle.getString("DATA_BUILD").split(" ");
        return Versao.builder()
                .data(dataHora[0])
                .versao(bundle.getString("VERSAO"))
                .projeto(bundle.getString("PROJETO")).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("exemplo")
    public void exemplo(@QueryParam("comando") String comando) throws Exception {

        int porta = 9001;
        String servidor = "localhost";
        InetSocketAddress adress = new InetSocketAddress(servidor, porta);

        SocketChannel cliente = SocketChannel.open();

        cliente.connect(adress);

        ByteBuffer b = ByteBuffer.wrap(comando.getBytes());

        int num = cliente.write(b);

        System.out.println(num);

//        byte[] resp = cliente.socket().getInputStream().readAllBytes();
//        System.out.println(new String(resp));
        cliente.finishConnect();

    }

}
