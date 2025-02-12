package br.gov.ce.sefaz.chati.pocketbase.user;

import br.gov.ce.sefaz.chati.core.BaseEntidade;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
public class User extends BaseEntidade {

    private String avatar;
    private String email;
    private String emailVisibility;
    private String name;
    private String verified;

}
