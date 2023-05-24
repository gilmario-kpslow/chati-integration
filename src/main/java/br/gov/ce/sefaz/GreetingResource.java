package br.gov.ce.sefaz;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    static GitPull gitPull = new GitPull();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("comando") String... comando) throws IOException {
        for (String string : comando) {
            System.out.println(string);

        }
        return gitPull.pull("git", "pull");
//        return "Hello from RESTEasy Reactive";
    }

}
