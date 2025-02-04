package br.gov.ce.sefaz.chati.pocketbase;

import jakarta.ws.rs.QueryParam;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
public class PageRequest {

    @QueryParam("page")
    Integer page;// = 1,
    Integer perPage;// = 30,
    Boolean skipTotal = false;// = false,
    String expand;
    String filter;
    String sort;
    String fields;

    public Integer getPage() {
        if (Objects.nonNull(page)) {
            return page;
        }
        return 1;
    }

    public Integer getParPage() {
        if (Objects.nonNull(perPage)) {
            return perPage;
        }
        return 1;
    }

}
