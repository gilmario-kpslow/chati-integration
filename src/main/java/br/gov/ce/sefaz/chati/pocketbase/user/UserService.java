package br.gov.ce.sefaz.chati.pocketbase.user;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import br.gov.ce.sefaz.chati.core.GenericService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
@RequestScoped
public class UserService extends GenericService<User> {

    @Inject
    UserDatabaseService databaseService;

    @Override
    protected void validar(User registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.nonNull(registro.getId()) && registro.getId().isBlank()) {
            registro.setId(null);
        }

    }

    @Override
    protected DatabaseService<User> getDatabaseService() {
        return databaseService;
    }

    public void login() throws IOException, InterruptedException {
        databaseService.login();
    }

}
