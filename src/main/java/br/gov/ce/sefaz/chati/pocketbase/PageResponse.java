package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.Entity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gilmario
 * @param <T>
 */
@Getter
@Setter
public class PageResponse<T extends Entity> {

    List<T> items;
    Integer page;
    Integer perPage;
    Integer totalItems;
    Integer totalPages;

}
