package br.gov.ce.sefaz.chati.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gilmario
 *
 */
@EqualsAndHashCode()
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntidade {

    protected String id;
    protected String collectionId;
    protected String collectionName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    protected LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    protected LocalDateTime updated;
}
